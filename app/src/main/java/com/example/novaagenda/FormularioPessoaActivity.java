package com.example.novaagenda;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.novaagenda.DAO.DAO;
import com.example.novaagenda.model.Pessoa;


public class FormularioPessoaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_pessoa);

        //pega as referencias dos EditText
        //campo Nome
        final EditText campoNome = findViewById(R.id.activity_formulario_pessoa_nome);
        //campo Telefone
        final EditText campoTelefone = findViewById(R.id.activity_formulario_pessoa_telefone);
        //campo E-mail
        final EditText campoEmail = findViewById(R.id.activity_formulario_pessoa_email);

        // pega os dados da pessoa selecionada
        Intent intent = getIntent();
        //Verifica os dados do extra e ve se tem a pessoa, se tiver ela pega os dados, se nao cria um aluno
        if (intent.hasExtra("nome")) {
            //Titulo do navbar
            setTitle("Editar Contato");
            //define os campos com os dados do contato em editar
            campoNome.setText(intent.getStringExtra("nome"));
            campoTelefone.setText(intent.getStringExtra("telefone"));
            campoEmail.setText(intent.getStringExtra("email"));
            //salva este dado nome da pessoa que é unico na variavel nomeantigo para alterar a pessoa certa
            String nomeantigo = intent.getStringExtra("nome");
            //define o botao
            Button botaoSalvar = findViewById(R.id.activity_formulario_pessoa_botao_salvar);
            botaoSalvar.setOnClickListener(view -> {
                //salva os novos valores de dados nas variaveis abaixo
                String nome = campoNome.getText().toString().trim();
                String telefone = campoTelefone.getText().toString().trim();
                String email = campoEmail.getText().toString().trim();
                //verifica se o campo nome é vazio
                String s1 = "";
                String s2 = campoNome.getText().toString();
                if (s1.equals(s2)) {
                    //caso seja vazio ele fecha sem salvar nada
                    finish();
                } else {
                    atualizaPessoaNoBanco(campoNome, campoTelefone, campoEmail, nomeantigo, nome, telefone, email);
                }
            });
        } else {
            //Titulo do navbar
            setTitle("Novo Contato");
            //define botao
            Button botaoSalvar = findViewById(R.id.activity_formulario_pessoa_botao_salvar);
            botaoSalvar.setOnClickListener(view -> {
                //salva os novos valores de dados nas variaveis abaixo
                String nome = campoNome.getText().toString().trim();
                String telefone = campoTelefone.getText().toString().trim();
                String email = campoEmail.getText().toString().trim();
                //verifica se o campo nome é vazio
                String s1 = "";
                String s2 = campoNome.getText().toString();
                if (s1.equals(s2)) {
                    finish();
                } else {
                    addPessoaNovanoBanco(campoNome, campoTelefone, campoEmail, nome, telefone, email);
                }
            });

        }
    }

    private void addPessoaNovanoBanco(EditText campoNome, EditText campoTelefone, EditText campoEmail, String nome, String telefone, String email) {
        //Insere no banco nova pessoa
        DAO dao = new DAO(getApplicationContext());
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setTelefone(telefone);
        pessoa.setEmail(email);
        try {
            dao.inserePessoa(pessoa);
            dao.close();

            //limpa os campos
            campoNome.setText("");
            campoNome.requestFocus();
            campoTelefone.setText("");
            campoEmail.setText("");

            Toast.makeText(this, "Contato Adicionado", Toast.LENGTH_SHORT).show();
            //finaliza a activity
            finish();
        } catch (Exception e){
            Toast.makeText(this, "Este nome já existe", Toast.LENGTH_SHORT).show();
        }
    }

    private void atualizaPessoaNoBanco(EditText campoNome, EditText campoTelefone, EditText campoEmail, String nomeantigo, String nome, String telefone, String email) {
        //Se não, Insere no banco nova pessoa
        DAO dao3 = new DAO(getApplicationContext());
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setTelefone(telefone);
        pessoa.setEmail(email);
        try {
            dao3.atualizaPessoa(nome, telefone, email, nomeantigo);
            dao3.close();

            //limpa os campos
            campoNome.setText("");
            campoNome.requestFocus();
            campoTelefone.setText("");
            campoEmail.setText("");

            Toast.makeText(this, "Alterado com Sucesso", Toast.LENGTH_SHORT).show();
            //finaliza a activity
            finish();
        } catch (Exception e){
            Toast.makeText(this, "Este nome já existe", Toast.LENGTH_SHORT).show();
            dao3.close();
        }
    }
}