package net.javaguides.springboot.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.javaguides.springboot.dtos.ApproveRejectDto;
import net.javaguides.springboot.dtos.ModerationRequestDto;
import net.javaguides.springboot.entities.ModerationRequest;
import net.javaguides.springboot.producers.ModeratorResponseProducer;
import net.javaguides.springboot.repository.ModerationRequestRepository;

@Service
@RequiredArgsConstructor
public class ModerationService {

  private final ModerationRequestRepository requestRepository;

  private KafkaTemplate<String, ApproveRejectDto> kafkaTemplate;

  public List<ModerationRequestDto> getProposedChanges(int page, int size) {
    Pageable pageReq = PageRequest.of(page, size);
    List<ModerationRequest> moderationRequests = requestRepository.findByStatus("PENDING", pageReq);
    ModelMapper modelMapper = new ModelMapper();
    List<ModerationRequestDto> mods = moderationRequests
                                      .stream()
                                      .map(modR-> modelMapper.map(modR, ModerationRequestDto.class))
                                      .collect(Collectors.toList());
    return mods;
  }

  public boolean decideChange(String uuid, String action) {
    Optional<ModerationRequest> moderationOpt = requestRepository.findById(uuid);
    if(!moderationOpt.isPresent()) return false;
    switch (action) {
      case "APPROVE":
        moderationOpt.get().setStatus(action);
        break;
      case "REJECT":
        moderationOpt.get().setStatus(action);
        break;
      default:
        return false;
    }
    requestRepository.save(moderationOpt.get());
    ModeratorResponseProducer moderatorResponseProducer = new ModeratorResponseProducer(kafkaTemplate);
    ApproveRejectDto approveRejectDto = new ApproveRejectDto();
    approveRejectDto.setUuid(uuid);
    approveRejectDto.setStatus(action);
    moderatorResponseProducer.sendMessage(approveRejectDto);
    return true;
  }
  
}
