package com.example.novaagenda.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.novaagenda.model.Pessoa;
import java.util.ArrayList;
import java.util.List;

public class DAO extends SQLiteOpenHelper {
    public DAO(Context context) {
        super(context, "banco" , null, 3);
    }
    //cria pela primeira vez o banco caso não tenha
    @Override
    public void onCreate (SQLiteDatabase db) {
        String sql = "CREATE TABLE pessoa(nome TEXT UNIQUE, telefone TEXT, email TEXT);";
        db.execSQL(sql);
    }
    //toda alteraçao do banco e mudar a versao para configurar um novo e apaga o antigo
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS pessoa;";
        db.execSQL(sql);
        onCreate(db);
    }
    //insere pessoa
    public void inserePessoa(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("nome", pessoa.getNome());
        dados.put("telefone", pessoa.getTelefone());
        dados.put("email", pessoa.getEmail());
        db.insertOrThrow("pessoa", null, dados);

    }
    //BUSCA TODOS no banco de dados
    @SuppressLint("Range")
    public List<Pessoa> buscaPessoa() {

        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM pessoa;";

        @SuppressLint("Recycle") Cursor c = db.rawQuery(sql, null);

        List<Pessoa> pessoas = new ArrayList<>();

        while (c.moveToNext()) {
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(c.getString(c.getColumnIndex("nome")));
            pessoa.setTelefone(c.getString(c.getColumnIndex("telefone")));
            pessoa.setEmail(c.getString(c.getColumnIndex("email")));
            pessoas.add(pessoa);
        }
        return pessoas;
    }
    //metodo para apagar do banco de dados
    public void apagaPessoa(String nome) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM pessoa WHERE nome = " + "'" +nome + "'";
        db.execSQL(sql);
    }
    //metodo para apagar do banco de dados
    public void atualizaPessoa(String novonome, String novotelefone, String novoemail, String nomeantigo) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE pessoa SET nome = " + "'" +novonome + "', telefone = " + "'" +novotelefone + "',  email = " + "'" +novoemail + "' WHERE nome = " + "'" +nomeantigo + "'";
        db.execSQL(sql);
    }
    //busca só a pessoa que quer

}
