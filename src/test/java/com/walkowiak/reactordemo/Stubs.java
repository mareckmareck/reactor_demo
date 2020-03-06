package com.walkowiak.reactordemo;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

public class Stubs {

    public static BigCityApartStub bigCityAparts() {
        return new BigCityApartStub();
    }

    public static CozyHousesApartStub cozyHousesAparts() {
        return new CozyHousesApartStub();
    }

    public static class BigCityApartStub extends ClientStub {

        @Override
        String getBody() {
            return "{\"apartments\":[{\"street\": \"Wielka 5\",\"size\": 43,\"price\": 2500 }]}";
        }

        @Override
        String getUrl() {
            return "/bigcityaparts/apartments";
        }
    }

    public static class CozyHousesApartStub extends ClientStub {

        @Override
        String getBody() {
            return "{\"apartments\":[{\"street\": \"Życzliwa 13\",\"size\": 55,\"price\": 2200 }]}";
        }

        @Override
        String getUrl() {
            return "/cozyhousesaparts/apartments";
        }
    }

    abstract public static class ClientStub {

        private int responseDelay;

        public ClientStub willRespondInMillis(int responseDelay) {
            this.responseDelay = responseDelay;
            return this;
        }

        public void withOneApartment() {
            stubFor(get(urlEqualTo(getUrl())).willReturn(
                    aResponse()
                            .withBody(getBody())
                            .withStatus(200)
                            .withFixedDelay(this.responseDelay)));
        }

        abstract String getBody();

        abstract String getUrl();
    }
}
