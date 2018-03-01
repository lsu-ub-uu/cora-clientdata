package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.Action;
import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;

public class JsonToDataActionLinkConverter implements JsonToDataConverter  {


    private JsonObject jsonObject;
    protected JsonToDataConverterFactory factory;
    private ActionLink actionLink;

    public JsonToDataActionLinkConverter(JsonObject jsonObject) {

        this.jsonObject = jsonObject;
    }

    public JsonToDataActionLinkConverter(JsonObject jsonObject, JsonToDataConverterFactory factory) {
        this.jsonObject = jsonObject;
        this.factory = factory;
    }

    public static JsonToDataConverter forJsonObject(JsonObject jsonObject) {
        return new JsonToDataActionLinkConverter(jsonObject);
    }

    public static JsonToDataConverter forJsonObjectUsingFactory(JsonObject jsonObject, JsonToDataConverterFactory factory) {
        return new JsonToDataActionLinkConverter(jsonObject, factory);
    }

    @Override

    public ClientDataElement toInstance() {
        validateRequiredKeys();
        createActionLinkAndAddRequiredValues();

        possiblyAddExtraValues();
        return actionLink;
    }

    private void validateRequiredKeys() {
        validateKey("rel");
        validateKey("url");
        validateKey("requestMethod");
    }

    private void validateKey(String key) {
        if(!jsonObject.containsKey(key)){
            throw new JsonParseException("Action link data must contain key: "+key);        }
    }

    private void createActionLinkAndAddRequiredValues() {
        actionLink = createActionLinkUsingRelInJsonObject();
        setUrl();
        setRequestMethod();
    }

    private ActionLink createActionLinkUsingRelInJsonObject() {
        String rel = getStringValueFromJsonObjectUsingKey("rel");
        return ActionLink.withAction(Action.valueOf(rel.toUpperCase()));
    }

    private String getStringValueFromJsonObjectUsingKey(String key) {
        return jsonObject.getValueAsJsonString(key).getStringValue();
    }

    private void setUrl() {
        String url = getStringValueFromJsonObjectUsingKey("url");
        actionLink.setURL(url);
    }

    private void setRequestMethod() {
        String requestMethod = getStringValueFromJsonObjectUsingKey("requestMethod");
        actionLink.setRequestMethod(requestMethod);
    }

    private void possiblyAddExtraValues() {
        possiblySetAccept();
        possiblySetContentType();
        possiblyConvertAndSetBody();
    }

    private void possiblySetContentType() {
        if(jsonObject.containsKey("contentType")) {
            String contentType = getStringValueFromJsonObjectUsingKey("contentType");
            actionLink.setContentType(contentType);
        }
    }

    private void possiblySetAccept() {
        if(jsonObject.containsKey("accept")) {
            String accept = getStringValueFromJsonObjectUsingKey("accept");
            actionLink.setAccept(accept);
        }
    }

    private void possiblyConvertAndSetBody() {
        if(jsonObject.containsKey("body")){
            JsonToDataConverter converter = factory.createForJsonObject(jsonObject.getValue("body"));
            ClientDataGroup bodyAsClientDataGroup = (ClientDataGroup) converter.toInstance();
            actionLink.setBody(bodyAsClientDataGroup);
        }
    }

}
