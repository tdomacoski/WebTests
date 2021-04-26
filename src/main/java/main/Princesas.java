package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerEmpty;
import arca.logger.LoggerFile;

import java.util.ArrayList;
import java.util.List;

public class Princesas {

    private static final Logger LOGGER = new LoggerEmpty();
    private static final Logger debug = new LoggerFile("Princesas_url");


    private static List<Localidade> getfull(final List<Localidade> lExpresso, final List<Localidade> lPrincesa) {
        final List<Localidade> full = new ArrayList<>(0);
        for (final Localidade l : lExpresso) {
            boolean tem = false;
            for (final Localidade f : full) {
                if (f.id.equals(l.id)) {
                    tem = true;
                    break;
                }
            }
            if (!tem) {
                full.add(l);
            }
        }
        for (final Localidade l : lPrincesa) {
            boolean tem = false;
            for (final Localidade f : full) {
                if (f.id.equals(l.id)) {
                    tem = true;
                    break;
                }
            }
            if (!tem) {
                full.add(l);
            }
        }
        return full;
    }

    public static void main(String[] args) throws Exception {

        final Operadora expresso = OperadoraIntegration.expressoPrincesaDosCampos();
        final Operadora princesa = OperadoraIntegration.princesWhiteLabel();

        final List<Localidade> lExpresso = ApiIntegration.getOrigens(expresso, LOGGER);
        debug.add(String.format("[%s] total localidades: %d", expresso.nome, lExpresso.size()));

        final List<Localidade> lPrincesa = ApiIntegration.getOrigens(princesa, LOGGER);
        debug.add(String.format("[%s] total localidades: %d", princesa.nome, lPrincesa.size()));



        final List<Localidade> full = getfull(lExpresso, lPrincesa);
        System.out.println("TOTAL DE LOCALIDADES : " + full.size());

        final List<Local> locals = new ArrayList<>(0);
        for (final Localidade l : full) {
            boolean temE = false;
            for (final Localidade ll : lExpresso) {
                if (l.id.equals(ll.id)) {
                    temE = true;
                    break;
                }
            }
            boolean temF = false;
            for (final Localidade ll : lPrincesa) {
                if (l.id.equals(ll.id)) {
                    temF = true;
                    break;
                }
            }
            locals.add(new Local(l, temE, temF));
        }


        final Logger todas = new LoggerFile("todas");
        final Logger whi = new LoggerFile("apenas_white_label");
        final Logger emb = new LoggerFile("apenas_embarca");

        for(final Local local: locals){
            if(local.embarca & !local.whiteLabel){
                emb.add(local.toString());
            }else if( local.whiteLabel & !local.embarca){
                whi.add(local.toString());
            }else if(local.embarca & local.whiteLabel){
                todas.add(local.toString());
            }

        }



    }


    static class Local {
        Local(final Localidade localidade, final Boolean embarca, final Boolean whiteLabel) {
            this.localidade = localidade;
            this.embarca = embarca;
            this.whiteLabel = whiteLabel;
        }

        public final Localidade localidade;
        public final Boolean embarca;
        public final Boolean whiteLabel;


        @Override
        public String toString() {
            return String.format("%s[%s] Embarca: %s , WHiteLabel: %s", localidade.cidade, localidade.id.toString(), (embarca?"SIM":"NÃO"), (whiteLabel?"SIM":"NÃO"));
        }
    }
}
