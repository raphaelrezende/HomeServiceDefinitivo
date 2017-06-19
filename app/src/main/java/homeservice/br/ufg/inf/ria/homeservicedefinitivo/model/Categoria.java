package homeservice.br.ufg.inf.ria.homeservicedefinitivo.model;

import com.orm.dsl.Table;

/**
 * Created by raphael on 18/05/17.
 */

@Table
public class Categoria {

    private Long id;
    private String nome;


    public Categoria() {
    }

    public Categoria(Long id, String nome) {
        this.id = id;
        this.nome = nome;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
