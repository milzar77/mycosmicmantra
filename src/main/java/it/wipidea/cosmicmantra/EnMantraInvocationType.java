package it.wipidea.cosmicmantra;

public enum EnMantraInvocationType {

    Test(-1), All(0), Cosmic(1), Christian(2), Bible(3), Latin(4), Angels(5), Secret(6), PrecettiCosmici(7), PrecettiCosmiciSintetici(8);

    Integer myflag;
    EnMantraInvocationType(int flag) {
        this.myflag = flag;
    }

}
