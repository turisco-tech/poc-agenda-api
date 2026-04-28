# POC Agenda: Arquitetura Moderna Cloud & DevOps (Java 21 + Angular)

Este projeto é uma Prova de Conceito (PoC) de uma aplicação completa de Agenda, projetada sob os pilares da Engenharia de Software moderna. Ele demonstra a integração ponta a ponta entre o desenvolvimento de software (Backend/Frontend) e a operação em nuvem (DevOps/AWS).

---

## 🏗️ Arquitetura da Solução

A aplicação foi concebida para ser resiliente, escalável e automatizada. Abaixo, detalhamos as camadas técnicas que compõem o ecossistema.

### 1. Backend (Java 21 & Spring Boot)
O núcleo da aplicação utiliza o estado da arte do ecossistema Java.
* **Teoria:** RESTful APIs, Component Scan, Injeção de Dependências.
* **Ação:** Implementação de uma API de status e fundamentos de CRUD.
* **Destaque Técnico:** Uso do **Java 21**, aproveitando melhorias de performance e sintaxe moderna.

### 2. Frontend (Angular & Nginx)
Interface responsiva e otimizada para o usuário final.
* **Teoria:** Single Page Application (SPA), Componentização, Web Servers leves.
* **Ação:** Aplicação Angular conteinerizada e servida através de um servidor **Nginx**.

### 3. Qualidade & CI (GitHub Actions & SonarCloud)
Garantia de que nenhum código entra em produção sem ser validado.
* **Engenharia de Software:** Análise Estática, Quality Gates, Automação de Pipeline.
* **Resultado:** Integração com **SonarCloud** para monitoramento de *Bugs*, *Vulnerabilidades* e *Code Smells* em tempo real a cada push.

### 4. Containerização (Docker)
Padronização do ambiente de execução.
* **Conceito:** Imutabilidade e Multi-stage Build.
* **Ação:** Dockerfiles otimizados que separam o ambiente de compilação do ambiente de execução, reduzindo drasticamente o tamanho das imagens finais.

### 5. Infraestrutura Cloud (AWS ECS & EC2)
Orquestração profissional utilizando o provedor líder de nuvem.
* **Teoria:** CaaS (Container as a Service), Networking (Security Groups), Gestão de Recursos.
* **Ação:** Deploy em um **Cluster ECS** rodando instâncias **t3.micro**.
* **Engenharia de Custos:** Ajuste fino de Task Definitions (CPU 0.25v/Memória 512MB) e estratégia de deploy (*Minimum Healthy Percent: 0%*) para maximizar o uso do Free Tier da AWS sem perda de disponibilidade.

---

## 🛠️ Passo a Passo para Reprodução

### Fase 1: Qualidade e Automação
1.  Vincule seus repositórios ao **SonarCloud**.
2.  Configure as chaves `SONAR_TOKEN` e as credenciais AWS nos *Secrets* do GitHub.
3.  Implemente o workflow do GitHub Actions em `.github/workflows/ci-cd.yml`.

### Fase 2: Infraestrutura AWS
1.  Crie um **ECR** (Elastic Container Registry) para armazenar as imagens da API e do Web.
2.  Configure um **Cluster ECS** baseado em EC2 (`t3.micro`).
3.  Crie as **Task Definitions** limitando recursos para caber no hardware escolhido.
4.  Crie os **Services** habilitando a comunicação via portas 80 (Web) e 8080 (API).

---

## 📈 Análise de Mercado & Visão de Carreira

Um profissional que domina este fluxo completo — da escrita do código em Java 21 ao troubleshooting de memória em instâncias EC2 — atinge o perfil de **Especialista/Arquiteto de Soluções**.

### O Arquiteto de 20k+
No mercado atual, salários acima de **R$ 20.000,00** são destinados a desenvolvedores que possuem a "visão sistêmica". Isso inclui:
* **Domínio de Cloud:** Não apenas codar, mas saber onde e como o código roda.
* **Cultura DevOps:** Automatizar para reduzir o erro humano.
* **Eficiência de Recursos:** Saber rodar aplicações complexas em infraestruturas enxutas.

---

## 🚀 Próximos Passos (Roadmap 2.0)

Este projeto está em constante evolução. As próximas etapas incluem:
- [ ] **Clean Architecture & DDD:** Refatoração do core para máxima testabilidade.
- [ ] **AI Agents Integration:** Uso de servidores MCP e agentes de IA para aceleração de desenvolvimento.
- [ ] **Mensageria com Kafka:** Implementação de comunicação assíncrona para processamento pesado.
- [ ] **Microsserviço de Dashboard:** Expansão para arquitetura distribuída e computação de grande porte.

---

## 📚 Práticas Sugeridas para Estudo
Para absorver este conhecimento, recomenda-se:
1.  **Simular falhas:** Tente subir uma imagem com erro no Sonar e veja o bloqueio.
2.  **Troubleshooting:** Monitore o uso de CPU/RAM no console da AWS enquanto faz requisições.
3.  **Networking:** Altere os Security Groups para entender como o tráfego é barrado ou liberado.

---
**Desenvolvido por Marcos Turisco** - Especialista em Java & Cloud Architecture.