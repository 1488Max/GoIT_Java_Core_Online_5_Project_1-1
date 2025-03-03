import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ParserMB {
    public static float buy (Currency currencyMB) throws IOException, InterruptedException, IllegalStateException {
        HttpClient client = HttpClient.newHttpClient();
        String uri = "https://api.monobank.ua/bank/currency";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonMB[] currencies = gson.fromJson(response.body(), JsonMB[].class);
        switch (currencyMB){
            case EUR :
                for (JsonMB monoBankCurrency : currencies) {
                    if (monoBankCurrency.getCurrencyCodeA()== 978) {
                        return monoBankCurrency.getRateBuy();
                    }
                }


            case USD :
                for (JsonMB monoBankCurrency : currencies) {
                    if (monoBankCurrency.getCurrencyCodeA() == 840) {
                        return monoBankCurrency.getRateBuy();
                    }
                }

        }
        return Float.parseFloat(null);
}
}
@Data
class JsonMB {
    private int currencyCodeA;
    private int currencyCodeB;
    private int date;
    private float rateSell;
    private float rateBuy;
    private float rateCross;

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
enum Currency {
    USD,
    EUR
}



