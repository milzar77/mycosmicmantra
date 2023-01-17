package it.wipidea.cosmicmantra;

public enum EnMantraInvocationType {

    Test(-1), All(0), Cosmic(1), Christian(2), Bible(3), Latin(4), Islam(5), Angels(6), Secret(7), PrecettiCosmici(8), PrecettiCosmiciSintetici(9);

    Integer myflag;
    EnMantraInvocationType(int flag) {
        this.myflag = flag;
    }

}
