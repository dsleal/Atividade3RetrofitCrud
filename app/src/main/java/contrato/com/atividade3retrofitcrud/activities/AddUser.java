package contrato.com.atividade3retrofitcrud.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class AddUser extends AppCompatActivity {
    private EditText name;
    private EditText userName;
    private EditText email;
    private EditText street;
    private EditText suite;
    private EditText city;
    private EditText zipCode;
    private EditText lat;
    private EditText lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
    }

    public void adicionar(View view){
        Users usr = getDadosTela();
        Retrofit retrofit = APIClient.getClient();
        UserResource userResource = retrofit.create(UserResource.class);
        Call<Users> post = userResource.post(usr);
        post.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users us = response.body();
                Toast.makeText(AddUser.this, "Usuario  '" + us.getName() + "' cadastrado!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddUser.this, UserList.class));
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(AddUser.this, "Erro ao cadastrar!", Toast.LENGTH_LONG).show();

            }
        });

    }

    private Users getDadosTela(){

        Address address = new Address();
        Company company = new Company();
        Geo geo = new Geo();
        Users user = new Users();

        name = findViewById(R.id.txtNome);
        userName  = findViewById(R.id.txtUserName);
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

        return user;
    }

    public void limpar(View view){

        name = findViewById(R.id.txtNome);
        userName  = findViewById(R.id.txtUserName);
        email = findViewById(R.id.txtEmail);
        street = findViewById(R.id.txtStreet);
        suite = findViewById(R.id.txtSuite);
        city = findViewById(R.id.txtCity);
        zipCode = findViewById(R.id.txtZipCode);
        lat = findViewById(R.id.txtLat);
        lng = findViewById(R.id.txtLng);

        name.setText(" ");
        userName.setText(" ");
        email.setText(" ");
        street.setText(" ");
        suite.setText(" ");
        city.setText(" ");
        zipCode.setText(" ");
        lat.setText(" ");
        lng.setText(" ");

    }

}
