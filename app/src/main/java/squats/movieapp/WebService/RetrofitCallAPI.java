package squats.movieapp.WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCallAPI {

    private static RetrofitAPIinterface retrofitAPIinterface = null;

    public static RetrofitAPIinterface getInstance (String baseUrl)
    {
        if(retrofitAPIinterface == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
             .baseUrl(baseUrl)
             .addConverterFactory(GsonConverterFactory.create())
             .build();
            retrofitAPIinterface = retrofit.create(RetrofitAPIinterface.class);
        }
        return retrofitAPIinterface;
    }
}
