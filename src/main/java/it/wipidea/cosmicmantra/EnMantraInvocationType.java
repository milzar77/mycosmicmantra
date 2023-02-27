package it.wipidea.cosmicmantra;

public enum EnMantraInvocationType {

    Test(-1), Custom(0), All(1), Cosmic(2), Christian(3), Bible(4), Latin(5), Islam(6), Angels(7), Secret(8), PrecettiCosmici(9), PrecettiCosmiciSintetici(10);

    Integer myflag;
    EnMantraInvocationType(int flag) {
        this.myflag = flag;
    }

}
