```
ftms-backend/
├── pom.xml                                 # Maven dependencies
├── Dockerfile                              # Container image
├── docker-compose.yml                      # Local development
├── kubernetes/
│   ├── deployment.yaml
│   ├── service.yaml
│   ├── configmap.yaml
│   └── secrets.yaml
│
├── src/main/java/com/ftms/
│   ├── FtmsApplication.java                # Spring Boot entry point
│   │
│   ├── shared/                             # Shared Kernel
│   │   ├── domain/
│   │   │   ├── Aggregate.java
│   │   │   ├── DomainEvent.java
│   │   │   ├── Repository.java
│   │   │   └── ValueObject.java
│   │   │
│   │   ├── infrastructure/
│   │   │   ├── config/
│   │   │   │   ├── JpaConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── CorsConfig.java
│   │   │   │   └── KafkaConfig.java
│   │   │   │
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       ├── DomainException.java
│   │   │       └── ApiErrorResponse.java
│   │   │
│   │   └── application/
│   │       ├── dto/
│   │       │   ├── PagedResponse.java
│   │       │   └── ErrorResponse.java
│   │       │
│   │       └── mapper/
│   │           └── GenericMapper.java
│   │
│   ├── customer/                           # Customer Bounded Context
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── Customer.java
│   │   │   │   ├── CustomerStatus.java
│   │   │   │   ├── KYCDocument.java
│   │   │   │   └── CustomerProfile.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   └── CustomerRepository.java
│   │   │   │
│   │   │   ├── service/
│   │   │   │   └── CustomerDomainService.java
│   │   │   │
│   │   │   └── event/
│   │   │       ├── CustomerCreatedEvent.java
│   │   │       ├── KYCVerifiedEvent.java
│   │   │       └── CustomerStatusChangedEvent.java
│   │   │
│   │   ├── application/
│   │   │   ├── usecase/
│   │   │   │   ├── CreateCustomerUseCase.java
│   │   │   │   ├── VerifyKYCUseCase.java
│   │   │   │   └── GetCustomerUseCase.java
│   │   │   │
│   │   │   ├── command/
│   │   │   │   ├── CreateCustomerCommand.java
│   │   │   │   └── VerifyKYCCommand.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── CreateCustomerRequest.java
│   │   │   │   ├── CustomerResponse.java
│   │   │   │   └── VerifyKYCRequest.java
│   │   │   │
│   │   │   └── mapper/
│   │   │       └── CustomerMapper.java
│   │   │
│   │   ├── infrastructure/
│   │   │   ├── repository/
│   │   │   │   ├── CustomerRepositoryAdapter.java
│   │   │   │   └── JpaCustomerRepository.java
│   │   │   │
│   │   │   ├── persistence/
│   │   │   │   └── CustomerJpaEntity.java
│   │   │   │
│   │   │   └── event/
│   │   │       └── CustomerEventHandler.java
│   │   │
│   │   └── api/
│   │       ├── CustomerController.java
│   │       └── CustomerApiDoc.java
│   │
│   ├── account/                            # Account Bounded Context
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── Account.java
│   │   │   │   ├── AccountType.java
│   │   │   │   ├── AccountStatus.java
│   │   │   │   └── AccountBalance.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   └── AccountRepository.java
│   │   │   │
│   │   │   └── event/
│   │   │       ├── AccountCreatedEvent.java
│   │   │       └── AccountClosedEvent.java
│   │   │
│   │   ├── application/
│   │   │   ├── usecase/
│   │   │   │   ├── CreateAccountUseCase.java
│   │   │   │   └── GetAccountUseCase.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── CreateAccountRequest.java
│   │   │   │   └── AccountResponse.java
│   │   │   │
│   │   │   └── mapper/
│   │   │       └── AccountMapper.java
│   │   │
│   │   ├── infrastructure/
│   │   │   ├── repository/
│   │   │   │   ├── AccountRepositoryAdapter.java
│   │   │   │   └── JpaAccountRepository.java
│   │   │   │
│   │   │   └── persistence/
│   │   │       └── AccountJpaEntity.java
│   │   │
│   │   └── api/
│   │       └── AccountController.java
│   │
│   ├── transaction/                        # Transaction Bounded Context
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   ├── Transaction.java
│   │   │   │   ├── TransactionLine.java
│   │   │   │   ├── TransactionStatus.java
│   │   │   │   └── Money.java
│   │   │   │
│   │   │   ├── repository/
│   │   │   │   └── TransactionRepository.java
│   │   │   │
│   │   │   └── event/
│   │   │       ├── TransactionInitiatedEvent.java
│   │   │       ├── TransactionCompletedEvent.java
│   │   │       ├── TransactionFailedEvent.java
│   │   │       └── TransactionReversedEvent.java
│   │   │
│   │   ├── application/
│   │   │   ├── usecase/
│   │   │   │   ├── CreateTransactionUseCase.java
│   │   │   │   ├── CompleteTransactionUseCase.java
│   │   │   │   └── GetTransactionUseCase.java
│   │   │   │
│   │   │   ├── command/
│   │   │   │   ├── CreateTransactionCommand.java
│   │   │   │   └── CompleteTransactionCommand.java
│   │   │   │
│   │   │   ├── dto/
│   │   │   │   ├── CreateTransactionRequest.java
│   │   │   │   └── TransactionResponse.java
│   │   │   │
│   │   │   └── mapper/
│   │   │       └── TransactionMapper.java
│   │   │
│   │   ├── infrastructure/
│   │   │   ├── repository/
│   │   │   │   ├── TransactionRepositoryAdapter.java
│   │   │   │   ├── JpaTransactionRepository.java
│   │   │   │   └── EventStoreRepository.java
│   │   │   │
│   │   │   ├── persistence/
│   │   │   │   ├── TransactionJpaEntity.java
│   │   │   │   └── StoredEvent.java
│   │   │   │
│   │   │   ├── event/
│   │   │   │   ├── DomainEventPublisher.java
│   │   │   │   └── EventStore.java
│   │   │   │
│   │   │   └── adapter/
│   │   │       ├── PaymentGatewayAdapter.java
│   │   │       └── PaymentGatewayTranslator.java
│   │   │
│   │   └── api/
│   │       └── TransactionController.java
│   │
│   └── compliance/                         # Compliance Bounded Context
│       ├── domain/
│       │   ├── model/
│       │   │   ├── ComplianceEvent.java
│       │   │   ├── AuditLog.java
│       │   │   └── ComplianceEventSeverity.java
│       │   │
│       │   └── repository/
│       │       └── ComplianceEventRepository.java
│       │
│       ├── application/
│       │   ├── usecase/
│       │   │   ├── GetAuditTrailUseCase.java
│       │   │   └── GenerateComplianceReportUseCase.java
│       │   │
│       │   ├── dto/
│       │   │   ├── ComplianceEventResponse.java
│       │   │   └── ComplianceReportResponse.java
│       │   │
│       │   └── mapper/
│       │       └── ComplianceEventMapper.java
│       │
│       ├── infrastructure/
│       │   ├── repository/
│       │   │   ├── ComplianceEventRepositoryAdapter.java
│       │   │   └── JpaComplianceEventRepository.java
│       │   │
│       │   ├── persistence/
│       │   │   └── ComplianceEventJpaEntity.java
│       │   │
│       │   ├── event/
│       │   │   └── ComplianceEventHandler.java
│       │   │
│       │   └── publisher/
│       │       └── ComplianceEventPublisher.java
│       │
│       └── api/
│           └── ComplianceController.java
│
└── src/test/java/com/ftms/
    ├── customer/
    │   ├── domain/
    │   │   └── CustomerTest.java
    │   ├── application/
    │   │   └── CreateCustomerUseCaseTest.java
    │   └── api/
    │       └── CustomerControllerTest.java
    │
    ├── transaction/
    │   ├── domain/
    │   │   └── TransactionTest.java
    │   ├── application/
    │   │   └── CreateTransactionUseCaseTest.java
    │   └── integration/
    │       └── TransactionIntegrationTest.java
    │
    └── support/
        ├── TestDataBuilder.java
        ├── MockFactories.java
        └── BaseIntegrationTest.java
```