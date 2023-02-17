package core.mvc;

import java.util.HashMap;
import java.util.Map;

public class ModelAndVIew {
    private View view;
    private Map<String, Object> models = new HashMap<>();

    public ModelAndVIew(View view) {
        this.view = view;
    }

    public ModelAndVIew addObject(String attributeName, Object attributeValue){
        models.put(attributeName, attributeValue);
        return this;
    }

    public View getView() {
        return view;
    }

    public Map<String, Object> getModels() {
        return models;
    }
}
