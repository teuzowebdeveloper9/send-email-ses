# Email Sender API (AWS SES)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.org/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-Build-blue)](https://maven.apache.org/)
[![AWS SES](https://img.shields.io/badge/AWS-SES-232F3E)](https://aws.amazon.com/ses/)

API REST simples para envio de e-mails via AWS SES, estruturada em camadas (Clean/Hexagonal style): Controller → Application Service → Core Use Case → Gateway → Infra (SES).

## Tecnologias
- Spring Boot (Web MVC)
- Java 17
- AWS SES SDK (v1 no sender, v2 já disponível para evolução)
- Maven Wrapper (`./mvnw`)

## Arquitetura rápida
- `Controllers/EmailSenderController`: endpoint `POST /api/v1/email/send`.
- `aplication/EmailSenderService`: orquestra o caso de uso.
- `core/emailSenderUseCase` + `adapters/EmailSenderGateway`: contratos de domínio.
- `infraestructure/ses/sesEmailSender`: implementação do gateway com AWS SES.
- `infraestructure/ses/AwsSesConfig`: bean `AmazonSimpleEmailService`.

## Configuração
1) Copie o exemplo e preencha credenciais/sender verificado:
```
cp src/main/resources/application-example.properties src/main/resources/application.properties
```
2) Ajuste:
```
aws.accessKeyId=YOUR_AWS_ACCESS_KEY_ID
aws.secretKey=YOUR_AWS_SECRET_ACCESS_KEY
aws.region=us-east-1
ses.fromEmail=verified-sender@example.com
```
- Em produção prefira variáveis de ambiente/credentials file da AWS.  
- `application.properties` já está no `.gitignore`.

## Rodando
```
./mvnw spring-boot:run
```

## Uso do endpoint
```
POST /api/v1/email/send
Content-Type: application/json
{
  "To": "destinatario@exemplo.com",
  "Subject": "Hello",
  "Body": "Mensagem de teste"
}
```
Resposta esperada: `200 OK` com `"email sent successfully"` ou `500` em caso de falha.

## Testes
```
./mvnw test
```

## Notas e próximos passos
- Substituir o remetente hardcoded em `sesEmailSender` para usar `ses.fromEmail` do properties/env.
- Adicionar validação e logging estruturado.
- Configurar IAM com política mínima para `ses:SendEmail` na região definida.

