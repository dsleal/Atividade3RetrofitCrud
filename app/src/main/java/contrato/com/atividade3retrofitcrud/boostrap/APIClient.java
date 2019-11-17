package contrato.com.atividade3retrofitcrud.boostrap;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    public static final String ENDPOINT = "https://jsonplaceholder.typicode.com/";
    //public static final String ENDPOINT = "http://192.168.107.9:8080/profile/";

    public static Retrofit getClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
