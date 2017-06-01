package homeservice.br.ufg.inf.ria.homeservicedefinitivo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;
import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Servico;

/**
 * Created by raphael on 01/06/17.
 */

public class ServicoDAO extends SQLiteOpenHelper {

    private static final String DB_NOME = "homeservice.db";
    private static final int DB_VERSION = 5;
    private static final String TABLE_SERVICOS = "servicos";

    //COLUMN_NOMES
    private static final String ROW_ID = "_idServico";
    private static final String ROW_NOME = "nome";
    private static final String ROW_DESCRICAO = "descricao";
    private static final String ROW_PRECO = "preco";
    private static final String ROW_CIDADE = "cidade";
    private static final String ROW_ID_CATEGORIA = "id_categoria";

    public ServicoDAO(Context context) {
        super(context, DB_NOME, null, DB_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if (!db.isReadOnly())
        {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_SERVICOS_TABLE = "CREATE TABLE " + TABLE_SERVICOS + "("
                + ROW_ID + " INTEGER PRIMARY KEY," + ROW_NOME + " TEXT,"
                + ROW_DESCRICAO + " TEXT," + ROW_PRECO + " REAL,"
                + ROW_CIDADE + " TEXT,"+ ROW_ID_CATEGORIA + " INTEGER,"+ " FOREIGN KEY ( "+ ROW_ID_CATEGORIA +" ) REFERENCES categorias( _idCategoria )" + " ON DELETE RESTRICT ON UPDATE CASCADE)";
        sqLiteDatabase.execSQL(CREATE_SERVICOS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICOS);
        onCreate(db);
    }

    public void create(Servico servico) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_ID,servico.getId());
        values.put(ROW_NOME, servico.getNome());
        values.put(ROW_DESCRICAO, servico.getDescricao());
        values.put(ROW_PRECO, servico.getPreco());
        values.put(ROW_CIDADE, servico.getCidade());
        values.put(ROW_ID_CATEGORIA, servico.getCategoria().getId());

        db.insert(TABLE_SERVICOS, null, values);
        db.close();
    }

    public List<Servico> getAll() {
        List<Servico> servicoList = new ArrayList<Servico>();

        String selectQuery = "SELECT  * FROM " + TABLE_SERVICOS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Servico servico = new Servico();
                servico.setId(Integer.parseInt(cursor.getString(0)));
                servico.setNome(cursor.getString(1));
                servico.setDescricao(cursor.getString(2));
                servico.setPreco(Double.parseDouble(cursor.getString(3)));
                servico.setCidade(cursor.getString(4));
                Categoria cat = new Categoria();
                cat.setId(Integer.parseInt(cursor.getString(5)));
                cat.setNome("categoria"+ cat.getId());
                cat.setDescricao("descricao" + cat.getId());
                servico.setCategoria(cat);
                // Adding contact to list
                servicoList.add(servico);
            } while (cursor.moveToNext());
        }

        return servicoList;
    }

    public Servico retrieve(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SERVICOS, new String[] { ROW_ID,
                        ROW_NOME, ROW_DESCRICAO,ROW_PRECO, ROW_CIDADE, ROW_ID_CATEGORIA }, ROW_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Servico servico = new Servico();
        servico.setId(Integer.parseInt(cursor.getString(0)));
        servico.setNome(cursor.getString(1));
        servico.setDescricao(cursor.getString(2));
        servico.setPreco(Double.parseDouble(cursor.getString(3)));
        servico.setCidade(cursor.getString(4));
        Categoria cat = new Categoria();
        cat.setId(Integer.parseInt(cursor.getString(5)));
        cat.setNome("categoria"+ cat.getId());
        cat.setDescricao("descricao" + cat.getId());
        servico.setCategoria(cat);

        return servico;
    }

    public int update(Servico servico) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_NOME, servico.getNome());
        values.put(ROW_DESCRICAO, servico.getDescricao());
        values.put(ROW_PRECO, servico.getPreco());
        values.put(ROW_CIDADE, servico.getCidade());
        values.put(ROW_ID,servico.getId());
        values.put(ROW_ID_CATEGORIA,servico.getCategoria().getId());

        // updating row
        return db.update(TABLE_SERVICOS, values, ROW_ID + " = ?",
                new String[] { String.valueOf(servico.getId()) });
    }

    public void delete(Servico servico) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SERVICOS, ROW_ID + " =?",
                new String[] { String.valueOf(servico.getId()) });
        db.close();
    }
}


