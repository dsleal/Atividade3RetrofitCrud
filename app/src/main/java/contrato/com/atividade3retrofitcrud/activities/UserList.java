package contrato.com.atividade3retrofitcrud.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import contrato.com.atividade3retrofitcrud.R;
import contrato.com.atividade3retrofitcrud.adapters.AdapterUsers;
import contrato.com.atividade3retrofitcrud.boostrap.APIClient;
import contrato.com.atividade3retrofitcrud.model.Users;
import contrato.com.atividade3retrofitcrud.resoure.UserResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserList extends AppCompatActivity {
    public TextView txtNome;
    public TextView txtEmail;
    public List<Users> lista = new ArrayList<>();
    public ListView minhaLista;
    public List<Users> listUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buscarDadosUsuario();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserList.this, AddUser.class));
            }
        });
    }


    protected  void onStart(){
        super.onStart();
        Retrofit retrofit = APIClient.getClient();
        UserResource userResource = retrofit.create(UserResource.class);
        Call<List<Users>> get = userResource.get();
        get.enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                minhaLista = findViewById(R.id.lista);
                listUsers = response.body();
                AdapterUsers adapterUsers = new AdapterUsers(getApplicationContext(), listUsers);
                minhaLista.setAdapter(adapterUsers);
                minhaLista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        Intent intent = new Intent(UserList.this, EditUser.class);
                        intent.putExtra("ID",listUsers.get(arg2).getId());
                        startActivity(intent);
                        return true;
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {}
        });



    }

    protected void onResume() {
        super.onResume();
        buscarDadosUsuario();
    }

    private void buscarDadosUsuario() {

        /*Pega a referencia do ENDPOINT e do converter(gson)
         * */
        Retrofit retrofit = APIClient.getClient();

        //Faz o 'bind' da instância do retrofit com interface
        //que contém as operações (GET,POST,PUT,DELETE)
        UserResource userResource = retrofit.create(UserResource.class);

        //Faz a chamada do serviço
        Call<List<Users>> get = userResource.get();

        get.enqueue(new Callback<List<Users>>() {

            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                //ENTRADA DE DADOS

                minhaLista = findViewById(R.id.lista);
                listUsers = response.body();

                for(Users user :  listUsers){
                    Users u = new Users(user.getName(), user.getEmail());
                    lista.add(u);
                }

                AdapterUsers clienteAdapter = new AdapterUsers(getApplicationContext(), lista);
                minhaLista.setAdapter(clienteAdapter);

            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                minhaLista = findViewById(R.id.lista);
            }
        });


    }


}
