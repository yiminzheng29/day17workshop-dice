package vttp.ssf.Day18ShoppingCart.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp.ssf.Day18ShoppingCart.services.DiceService;

@RestController
@RequestMapping("/rolls")
public class DiceRestController {
    
    @Autowired
    private DiceService diceSvc;

    @GetMapping(produces="text/csv")
    public ResponseEntity<String> getRollasCSV(
        @RequestParam(name="dice", defaultValue="1") Integer count) {
            if ((count<1) || (count>10)) {
                String error = "Error: Valid dice count is between 1 and 10. Your count is %d.".formatted(count);
                return ResponseEntity.badRequest().body(error);
            }

            List<Integer> rolls = diceSvc.roll(count);
            String csv = rolls.stream().map(v -> v.toString()).collect(Collectors.joining(","));
            return ResponseEntity.ok(csv);
        }

    @GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getRollasJson(
        @RequestParam(name="dice", defaultValue="1") Integer count) {
            if ((count<1) || (count>10)) {
                JsonObject errResp = Json.createObjectBuilder()
                    .add("error", "Valid dice count is between 1 and 10. Your count is %d".formatted(count))
                    .build();

                String payload = errResp.toString();
                return ResponseEntity.badRequest().body(payload);
            }
            List<Integer> rolls = diceSvc.roll(count);
            JsonObjectBuilder jo = Json.createObjectBuilder().add("count", count);
            JsonArrayBuilder ja = Json.createArrayBuilder();
            for (Integer d: rolls) {
                ja.add(d);
            }
            jo.add("rolls", ja);

            JsonObject resp = jo.build();
            return ResponseEntity.ok(resp.toString());
        }
}
