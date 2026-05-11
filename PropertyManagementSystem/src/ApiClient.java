import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.swing.DefaultComboBoxModel;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:3000/api";
    private static String token = "";
    private static int userType = 0;
    private static String loggedInName = "";
    private static String loggedInUsername = "";

    public static boolean login(String username, String password) throws Exception {
        String body = "{\"username\":\"" + esc(username) + "\",\"password\":\"" + esc(password) + "\"}";
        String response = request("POST", "/auth/login", body);

        String extractedToken = extractValue(response, "token");

        if (extractedToken != null && !extractedToken.isEmpty()) {
        token = extractedToken;

        String typeValue = extractUserField(response, "type");
        String nameValue = extractUserField(response, "name");
        String usernameValue = extractUserField(response, "username");

        try {
            userType = Integer.parseInt(typeValue);
        } catch (Exception e) {
            userType = 0;
         }

            loggedInName = nameValue == null ? "" : nameValue;
            loggedInUsername = usernameValue == null ? "" : usernameValue;

            return true;
        }

        throw new Exception(response);
    }
    
    private static String extractUserField(String json, String key) {
        int userIndex = json.indexOf("\"user\":");
        if (userIndex < 0) return null;

        int objStart = json.indexOf('{', userIndex);
        int objEnd = findMatching(json, objStart, '{', '}');

        if (objStart < 0 || objEnd < 0) return null;

        String userObject = json.substring(objStart + 1, objEnd);
        Map<String, String> userMap = parseObject(userObject);

        return userMap.get(key);
    }
    
    public static int getUserType() {
        return userType;
    }

    public static String getLoggedInName() {
        return loggedInName;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static boolean isLoggedIn() {
        return token != null && !token.isEmpty();
    }

    public static void logout() {
        token = "";
        userType = 0;
        loggedInName = "";
        loggedInUsername = "";
    }

    public static DefaultTableModel getPropertiesTableModel() throws Exception {
        String[] columns = {"property_id", "house_no", "address", "owner_no", "owner_id", "p_type", "rooms", "property_rent"};
        return toTableModel(request("GET", "/desktop/properties", null), columns);
    }

    public static DefaultTableModel getTenantsTableModel() throws Exception {
        String[] columns = {"tenant_id", "tenant_no", "owner_id", "property_rent", "start_date", "end_date", "tenant_name", "property_id", "house_no"};
        return toTableModel(request("GET", "/desktop/tenants", null), columns);
    }
    
    public static DefaultComboBoxModel<String> getCategoriesComboBoxModel() throws Exception {
    String response = request("GET", "/desktop/categories", null);
    ensureSuccess(response);

    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
    List<Map<String, String>> rows = parseDataObjects(response);

    for (Map<String, String> row : rows) {
        String name = row.getOrDefault("name", "");
        if (!name.trim().isEmpty()) {
            model.addElement(name);
        }
    }

    return model;
}

    public static void addProperty(String propertyId, String address, String ownerNo, String ownerId, String propertyType, String rooms, String rent) throws Exception {
        String body = propertyJson(propertyId, address, ownerNo, ownerId, propertyType, rooms, rent);
        ensureSuccess(request("POST", "/desktop/properties", body));
    }

    public static void updateProperty(String id, String propertyId, String address, String ownerNo, String ownerId, String propertyType, String rooms, String rent) throws Exception {
        String body = propertyJson(propertyId, address, ownerNo, ownerId, propertyType, rooms, rent);
        ensureSuccess(request("PUT", "/desktop/properties/" + enc(id), body));
    }

    public static void deleteProperty(String id) throws Exception {
        ensureSuccess(request("DELETE", "/desktop/properties/" + enc(id), null));
    }

    public static void addTenant(String tenantNo, String propertyId, String rent, String startDate, String endDate, String tenantName) throws Exception {
        ensureSuccess(request("POST", "/desktop/tenants", tenantJson(tenantNo, propertyId, rent, startDate, endDate, tenantName)));
    }

    public static void updateTenant(String tenantId, String tenantNo, String propertyId, String rent, String startDate, String endDate, String tenantName) throws Exception {
        ensureSuccess(request("PUT", "/desktop/tenants/" + enc(tenantId), tenantJson(tenantNo, propertyId, rent, startDate, endDate, tenantName)));
    }

    public static void deleteTenant(String tenantId) throws Exception {
        ensureSuccess(request("DELETE", "/desktop/tenants/" + enc(tenantId), null));
    }

    private static String propertyJson(String propertyId, String address, String ownerNo, String ownerId, String propertyType, String rooms, String rent) {
        return "{"
                + "\"property_id\":\"" + esc(propertyId) + "\","
                + "\"address\":\"" + esc(address) + "\","
                + "\"owner_no\":\"" + esc(ownerNo) + "\","
                + "\"owner_id\":\"" + esc(ownerId) + "\","
                + "\"p_type\":\"" + esc(propertyType) + "\","
                + "\"rooms\":\"" + esc(rooms) + "\","
                + "\"property_rent\":" + numberOrZero(rent)
                + "}";
    }

    private static String tenantJson(String tenantNo, String propertyId, String rent, String startDate, String endDate, String tenantName) {
        return "{"
                + "\"tenant_no\":\"" + esc(tenantNo) + "\","
                + "\"property_id\":\"" + esc(propertyId) + "\","
                + "\"property_rent\":" + numberOrZero(rent) + ","
                + "\"start_date\":\"" + esc(startDate) + "\","
                + "\"end_date\":\"" + esc(endDate) + "\","
                + "\"tenant_name\":\"" + esc(tenantName) + "\""
                + "}";
    }

    private static String request(String method, String endpoint, String body) throws Exception {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(method);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Content-Type", "application/json");
        if (!token.isEmpty()) con.setRequestProperty("Authorization", "Bearer " + token);
        if (body != null) {
            con.setDoOutput(true);
            try (OutputStream os = con.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
        }
        int code = con.getResponseCode();
        InputStream stream = code >= 200 && code < 300 ? con.getInputStream() : con.getErrorStream();
        String response = readAll(stream);
        if (code < 200 || code >= 300) throw new Exception(response);
        return response;
    }

    private static String readAll(InputStream stream) throws Exception {
        if (stream == null) return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) sb.append(line);
        return sb.toString();
    }

    private static void ensureSuccess(String response) throws Exception {
        if (!response.contains("\"success\":true")) throw new Exception(response);
    }

    private static DefaultTableModel toTableModel(String response, String[] columns) throws Exception {
        ensureSuccess(response);
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        List<Map<String, String>> rows = parseDataObjects(response);
        for (Map<String, String> row : rows) {
            Object[] values = new Object[columns.length];
            for (int i = 0; i < columns.length; i++) values[i] = row.getOrDefault(columns[i], "");
            model.addRow(values);
        }
        return model;
    }

    private static List<Map<String, String>> parseDataObjects(String json) {
        List<Map<String, String>> rows = new ArrayList<>();
        int dataIndex = json.indexOf("\"data\":");
        if (dataIndex < 0) return rows;
        int arrayStart = json.indexOf('[', dataIndex);
        int arrayEnd = findMatching(json, arrayStart, '[', ']');
        if (arrayStart < 0 || arrayEnd < 0) return rows;
        String array = json.substring(arrayStart + 1, arrayEnd);
        int i = 0;
        while (i < array.length()) {
            int objStart = array.indexOf('{', i);
            if (objStart < 0) break;
            int objEnd = findMatching(array, objStart, '{', '}');
            if (objEnd < 0) break;
            rows.add(parseObject(array.substring(objStart + 1, objEnd)));
            i = objEnd + 1;
        }
        return rows;
    }

    private static Map<String, String> parseObject(String obj) {
        Map<String, String> map = new LinkedHashMap<>();
        int i = 0;
        while (i < obj.length()) {
            int keyStart = obj.indexOf('"', i);
            if (keyStart < 0) break;
            int keyEnd = findStringEnd(obj, keyStart + 1);
            if (keyEnd < 0) break;
            String key = unescape(obj.substring(keyStart + 1, keyEnd));
            int colon = obj.indexOf(':', keyEnd);
            if (colon < 0) break;
            int valueStart = colon + 1;
            while (valueStart < obj.length() && Character.isWhitespace(obj.charAt(valueStart))) valueStart++;
            String value;
            if (valueStart < obj.length() && obj.charAt(valueStart) == '"') {
                int valueEnd = findStringEnd(obj, valueStart + 1);
                value = valueEnd >= 0 ? unescape(obj.substring(valueStart + 1, valueEnd)) : "";
                i = valueEnd + 1;
            } else {
                int valueEnd = valueStart;
                while (valueEnd < obj.length() && obj.charAt(valueEnd) != ',') valueEnd++;
                value = obj.substring(valueStart, valueEnd).trim();
                if ("null".equals(value)) value = "";
                i = valueEnd + 1;
            }
            map.put(key, value);
        }
        return map;
    }

    private static int findMatching(String s, int start, char open, char close) {
        if (start < 0) return -1;
        int depth = 0;
        boolean inString = false;
        for (int i = start; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '"' && (i == 0 || s.charAt(i - 1) != '\\')) inString = !inString;
            if (!inString) {
                if (c == open) depth++;
                if (c == close) {
                    depth--;
                    if (depth == 0) return i;
                }
            }
        }
        return -1;
    }

    private static int findStringEnd(String s, int start) {
        for (int i = start; i < s.length(); i++) {
            if (s.charAt(i) == '"' && (i == 0 || s.charAt(i - 1) != '\\')) return i;
        }
        return -1;
    }

    private static String extractValue(String json, String key) {
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern);
        if (start < 0) return null;
        start += pattern.length();
        int end = findStringEnd(json, start);
        return end >= 0 ? unescape(json.substring(start, end)) : null;
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    private static String unescape(String s) {
        return s.replace("\\\"", "\"").replace("\\n", "\n").replace("\\\\", "\\");
    }

    private static String enc(String s) {
        return s == null ? "" : s.replace(" ", "%20");
    }

    private static String numberOrZero(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return "0";
            return String.valueOf(Double.parseDouble(s.trim()));
        } catch (Exception e) {
            return "0";
        }
    }
}
