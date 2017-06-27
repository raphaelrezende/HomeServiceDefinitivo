package homeservice.br.ufg.inf.ria.homeservicedefinitivo.model;

import com.orm.dsl.Table;

/**
 * Created by raphael on 27/06/17.
 */

@Table
public class Venda {

    private Long id;
    private Servico servico;
    private Endereco endereco;
    private Cartao cartao;

    public Venda() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }
}
