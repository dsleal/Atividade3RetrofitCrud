package contrato.com.atividade3retrofitcrud.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import contrato.com.atividade3retrofitcrud.R;
import contrato.com.atividade3retrofitcrud.boostrap.APIClient;
import contrato.com.atividade3retrofitcrud.model.Address;
import contrato.com.atividade3retrofitcrud.model.Company;
import contrato.com.atividade3retrofitcrud.model.Geo;
import contrato.com.atividade3retrofitcrud.model.Users;
import contrato.com.atividade3retrofitcrud.resoure.UserResource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditUser extends AppCompatActivity {
    private EditText name;
    private EditText userName;
    private EditText email;
    private EditText street;
    private EditText suite;
    private EditText city;
    private EditText zipCode;
    private EditText lat;
    private EditText lng;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        name = findViewById(R.id.txtNome);
        userName = findViewById(R.id.txtUserName);
        email = findViewById(R.id.txtEmail);
        street = findViewById(R.id.txtStreet);
        suite = findViewById(R.id.txtSuite);
        city = findViewById(R.id.txtCity);
        zipCode = findViewById(R.id.txtZipCode);
        lat = findViewById(R.id.txtLat);
        lng = findViewById(R.id.txtLng);

        Intent intent = getIntent();
        id = intent.getIntExtra("ID", 0);

        Retrofit retrofit = APIClient.getClient();
        UserResource userResource = retrofit.create(UserResource.class);
        Call<Users> get = userResource.getPorId(id);
        get.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users usr = response.body();
                name.setText(usr.getName());
                userName.setText(usr.getUsername());
                email.setText(usr.getEmail());
                street.setText(usr.getAddress().getStreet());
                suite.setText(usr.getAddress().getSuite());
                city.setText(usr.getAddress().getCity());
                zipCode.setText(usr.getAddress().getZipcode());
                lat.setText(usr.getAddress().getGeo().getLat());
                lng.setText(usr.getAddress().getGeo().getLng());
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
            }
        });
    }

    public void remover(View view) {
        Retrofit retrofit = APIClient.getClient();
        UserResource userResource = retrofit.create(UserResource.class);
        Call<Void> delete = userResource.delete(id);
        delete.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getBaseContext(), "Usuario removido com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditUser.this, UserList.class));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Não foi possível remover o usuario!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void alterar(View view) {

        Address address = new Address();
        Company company = new Company();
        Geo geo = new Geo();
        Users user = new Users();

        name = findViewById(R.id.txtNome);
        userName = findViewById(R.id.txtUserName);
        email = findViewById(R.id.txtEmail);
        street = findViewById(R.id.txtStreet);
        suite = findViewById(R.id.txtSuite);
        city = findViewById(R.id.txtCity);
        zipCode = findViewById(R.id.txtZipCode);
        lat = findViewById(R.id.txtLat);
        lng = findViewById(R.id.txtLng);

        //Não inseri esses campos na tela para capturar
        company.setNome(" ");
        company.setBs(" ");
        company.setCatchphrase(" ");
        user.setPhone(" ");
        user.setWebsite(" ");

        //Inseridos na tela
        geo.setLat(lat.getText().toString());
        geo.setLng(lat.getText().toString());
        address.setGeo(geo);
        address.setZipcode(zipCode.getText().toString());
        address.setCity(city.getText().toString());
        address.setSuite(suite.getText().toString());
        address.setStreet(street.getText().toString());
        user.setCompany(company);
        user.setAddress(address);
        user.setEmail(email.getText().toString());
        user.setUsername(userName.getText().toString());
        user.setName(name.getText().toString());


        Retrofit retrofit = APIClient.getClient();
        UserResource userResource = retrofit.create(UserResource.class);
        Call<Users> alterar = userResource.put(id, user);
        alterar.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users usr = response.body();
                Toast.makeText(getBaseContext(), "Usuario alterado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditUser.this, UserList.class));
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Não foi possível alterar o usuario!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
