    package com.Ambientese.Empresa.Model;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;

    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.NotNull;

    @Entity
    public class EnderecoModel {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @NotNull
        @NotBlank
        private String cep;
        @NotNull
        @NotBlank
        private Integer numero;
        @NotNull
        @NotBlank
        private String logradouro;
        private String complemento;
        @NotNull
        @NotBlank
        private String cidade;
        @NotNull
        @NotBlank
        private String bairro;
        @NotNull
        @NotBlank
        private String UF;

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public Integer getNumero() {
            return numero;
        }

        public void setNumero(Integer numero) {
            this.numero = numero;
        }

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getComplemento() {
            return complemento;
        }

        public void setComplemento(String complemento) {
            this.complemento = complemento;
        }

        public String getCidade() {
            return cidade;
        }

        public void setCidade(String cidade) {
            this.cidade = cidade;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public String getUF() {
            return UF;
        }

        public void setUF(String uF) {
            UF = uF;
        }

    }

