package easycommute.EaCeWithMetro.models.MyTokens;

import java.util.List;

public class Response
{
    private List<TokenEntry> tokenHistory = null;

    public List<TokenEntry> getResponse() {
        return tokenHistory;
    }

    public void setResponse(List<TokenEntry> response) {
        this.tokenHistory = response;
    }
}
