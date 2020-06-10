# WebTests

## Descrição
Projeto em Java estruturado através do maven paa realizar consultas e validar através da ferramenta de teste [selenium](https://www.alura.com.br/conteudo/selenium). 
O projeto se divide em duas partes, sendo o projeto principal a consulta na API e os testes voltados para o [site](https://app.embarca.ai/).
O objetivo dos testes não é validar as consultas, e sim o site, sendo o projeto um auxiliar para validação dos testes.  

### Objetivos 

- Realizar teste de conexão com a API de  Vendas da RJ.
- Testar o fluxo de compras de uma passagem. 
- Validar as informações do site (através do selenium).

### Arquitetura 

- [Clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) 

### Plugins 

- org.seleniumhq.selenium (4.0.0-alpha-5)
- junit (4.13)
- com.google.code.gson (2.8.6)

### Instalação
Para funcionamento do selenium é necessário realizar o download do [chromeWebDriver](https://chromedriver.chromium.org/downloads). 
Após download é necessário informar a sua localização no arquivo ```WebDriverUtils```.


