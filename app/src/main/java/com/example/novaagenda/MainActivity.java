package com.example.novaagenda;

//imports
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.novaagenda.DAO.DAO;
import com.example.novaagenda.model.Pessoa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;


// usa-se a herança para funcionar a activity: ..extends Activity
// mas para criar a APPBAR, troque por AppCompatActivity
public class MainActivity extends AppCompatActivity {

    //durante a cricação da activity - estado
    //tudo o que estiver dentro activity precisa estar aqui
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //define o título do APPBAR
        setTitle("Agenda Oculta");
        //Chama e define o layout estático como principal
        setContentView(R.layout.activity_main);
        //se tirar esse linha fica sem nav bar adaptado para modo noturno
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.purple_500)));

        //botao nova pessoa
        FloatingActionButton botaoNovoAluno = findViewById(R.id.activity_main_botao_nova_pessoa);
        botaoNovoAluno.setOnClickListener(view -> {
            //quando clicado abre a tela formulario pessoa
            startActivity(new Intent(MainActivity.this,FormularioPessoaActivity.class));
        });
    }
    //atualiza a activity quando aberta outra ou tenta voltar
    @Override
    protected void onResume() {
        super.onResume();
        //pega do banco de dados
        DAO dao2 = new DAO(getApplicationContext());

        //define os contatos
        List<Pessoa> pessoas = dao2.buscaPessoa();
        //nova lista - principal
        List<String> nomes = new ArrayList<>();
        List<String> telefones = new ArrayList<>();
        List<String> emails = new ArrayList<>();

        //String[] dados_nomes = new String[] {};
        //String[] dados_telefones = new String[] {};
        //String[] dados_emails = new String[] {};

        //pega só o nome e add em nova lista
        for (Pessoa nomeBuscado : pessoas) {
            nomes.add(nomeBuscado.getNome());
            telefones.add(nomeBuscado.getTelefone());
            emails.add(nomeBuscado.getEmail());
        }

        //dados_nomes = nomes.toArray(new String[0]);
        //dados_telefones = telefones.toArray(new String[0]);
        //dados_emails = emails.toArray(new String[0]);

        //passa a lista de alunos para uma variavel
        ListView ListaDeContatos = findViewById(R.id.activity_main_lista_de_contatos);

        //esse para alterar a cor da linha
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.row, nomes);

        //original
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, nomes);

        //onde fara as linhas e os nomes
        ListaDeContatos.setAdapter(adapter);
        //clique longo - resolver pois não atualiza o apagamento da pessoa, so quando abre novamente
        ListaDeContatos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicao, long id) {


                String s = nomes.get(posicao);
                Toast.makeText(MainActivity.this, ""+s+" Foi Removido", Toast.LENGTH_SHORT).show();
                //apaga pessoa com base no nome
                DAO dao = new DAO(getApplicationContext());
                dao.apagaPessoa(s);
                adapter.remove(s);
                return true;
            }
        });
        //edita formulario Click Normal
        ListaDeContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                String pessoaSelecionada = nomes.get(posicao);
                Toast.makeText(MainActivity.this, "Pessoa: "+pessoaSelecionada, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, FormularioPessoaActivity.class);
                intent.putExtra("nome", nomes.get(posicao));
                intent.putExtra("telefone", telefones.get(posicao));
                intent.putExtra("email", emails.get(posicao));
                //inicia a activity para transferir os dados
                startActivity(intent);
            }
        });

    }
}
