package net.javaguides.springboot.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.dtos.ModerationRequestDto;
import net.javaguides.springboot.services.ModerationService;

@RestController
@RequestMapping("/mod")
public class ModerationController {

  private final ModerationService service;

  public ModerationController(ModerationService service) {
    this.service = service;
  }

  @GetMapping("/proposed")
  public List<ModerationRequestDto> getProposedChanges(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    return service.getProposedChanges(page, size);
  }

  @PatchMapping("/{id}/decision")
  public ResponseEntity<String> decideChange(
    @PathVariable String id,
    @RequestParam("action") String action // "approve" or "reject"
  ) {
    boolean updated = service.decideChange(id, action);
    return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }


}
