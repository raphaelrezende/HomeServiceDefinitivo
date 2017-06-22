package homeservice.br.ufg.inf.ria.homeservicedefinitivo.model;

import com.orm.dsl.Table;

/**
 * Created by raphael on 22/06/17.
 */

@Table
public class Endereco {

    private Long id;
    private String cep;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String complemento;
    private String observacoes;

    public Endereco() {
    }

    public Endereco(String cep, String logradouro, String cidade, String bairro, String complemento, String observacoes) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.bairro = bairro;
        this.complemento = complemento;
        this.observacoes = observacoes;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
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

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
