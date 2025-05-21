# wikimedia-microservices-demo

Started with: https://www.youtube.com/watch?v=inrQUHLPFd4
and then incorporated some ideas from: https://www.youtube.com/watch?v=YbSC1OsLp20
and then considered: https://patroclosdev.medium.com/spring-boot-3-microservices-orchestrator-with-reactive-programming-apache-kafka-b8b531048126

Concepts Exploration:
Explain and build a complex data structure 
Build an orchestrated API 
JSON Payload explanation
Understand what sort of id system is useful from a transaction tracing POV (uuids?)
How is an Orchestrated API fault tolerant 
Pagination of results


WikiMedia change stream produces a 20+ field data structure, some are embedded and possess overlapping fields. It uses a LONG format of the UNIX timestamp, and various data fields need to be parse for storing into the db.
Overall an orchestrated API is the one of leads in a micro service architecture, as it is a service that uses other services to complete a flow. (IE Saga Pattern) In this case. WikiMedia changes stream into the Orchestrator API, which then proceeds to asynchronously queue requests to a ModerationAPI via a Kafka Topic. Kafka facilitates asynchronous communication between both ModerationAPI and the Orchestrator API, as Moderation will go slowly from manual review, while the wikimedia changes are in the range of 20-30 requests per second. After an approve or reject happens, it notifies a KafkaConsumer on the Orchestrator API. The Orchestrator then also asynchrously calls a POST endpoint for completing the review and to send user feedback. This asynchronous call is done with a web client that has jitter and a circuit breaker. UUIDS are how we can track and prove the idempotency of a transaction, it’s how we can search a db to rollback a change or to cancel an order. An Orchestrated API is fault tolerant bc of the Asynchronous handling that it passes on to the other micro services. Additionally in the ModerationAPI we have expressed a way to view the changes being requested using a pagination technique.