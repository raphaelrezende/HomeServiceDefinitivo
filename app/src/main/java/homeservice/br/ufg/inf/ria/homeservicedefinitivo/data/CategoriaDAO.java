package homeservice.br.ufg.inf.ria.homeservicedefinitivo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import homeservice.br.ufg.inf.ria.homeservicedefinitivo.model.Categoria;

/**
 * Created by raphael on 18/05/17.
 */

public class CategoriaDAO extends SQLiteOpenHelper {

    private static final String DB_NOME = "homeservice.db";
    private static final int DB_VERSION = 5;
    private static final String TABLE_CATEGORIAS = "categorias";

    //COLUMN_NOMES
    private static final String ROW_ID = "_idCategoria";
    private static final String ROW_NOME = "nome";
    private static final String ROW_DESCRICAO = "descricao";

    public CategoriaDAO(Context context) {
        super(context, DB_NOME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CATEGORIAS + "("
                + ROW_ID + " INTEGER PRIMARY KEY," + ROW_NOME + " TEXT,"
                + ROW_DESCRICAO + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIAS);
        onCreate(db);
    }

    public void create(Categoria categoria) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_ID,categoria.getId());
        values.put(ROW_NOME, categoria.getNome());
        values.put(ROW_DESCRICAO, categoria.getDescricao());


        db.insert(TABLE_CATEGORIAS, null, values);
        db.close();
    }

    public List<Categoria> getAll() {
        List<Categoria> categoriaList = new ArrayList<Categoria>();

        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIAS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Categoria categoria = new Categoria();
                categoria.setId(Integer.parseInt(cursor.getString(0)));
                categoria.setNome(cursor.getString(1));
                categoria.setDescricao(cursor.getString(2));
                // Adding contact to list
                categoriaList.add(categoria);
            } while (cursor.moveToNext());
        }

        return categoriaList;
    }

    public Categoria retrieve(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORIAS, new String[] { ROW_ID,
                        ROW_NOME, ROW_DESCRICAO }, ROW_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Categoria categoria = new Categoria();
        categoria.setId(Integer.parseInt(cursor.getString(0)));
        categoria.setNome(cursor.getString(1));
        categoria.setDescricao(cursor.getString(2));

        return categoria;
    }

    public int update(Categoria categoria) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ROW_NOME, categoria.getNome());
        values.put(ROW_DESCRICAO, categoria.getDescricao());
        values.put(ROW_ID,categoria.getId());

        // updating row
        return db.update(TABLE_CATEGORIAS, values, ROW_ID + " = ?",
                new String[] { String.valueOf(categoria.getId()) });
    }

    public void delete(Categoria categoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIAS, ROW_ID + " = ?",
                new String[] { String.valueOf(categoria.getId()) });
        db.close();
    }
}
