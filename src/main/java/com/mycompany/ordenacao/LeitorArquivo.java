package com.mycompany.ordenacao;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Collections;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class LeitorArquivo {

  
    public static void main(String[] args) {
        try {
            
            //lê o arquivo json
            Path path = Paths.get("C:\\Users\\manu\\Desktop\\Ordenacao\\src\\main\\java\\com\\mycompany\\ordenacao\\meu_json.json");
            
            //converte o arquivo lido para uma string
            String conteudo = new String(Files.readAllBytes(path));
            
            //converte a string em um objeto JSONArray usando a biblioteca JSON.
            JSONArray jsonArray = new JSONArray(conteudo);

            //Lista utilizada para mapear os valores inteiros da lista
            Map<Integer, List<JSONObject>> arqToObjectsMap = new HashMap<>();

            //percorre a lista para obter o valor "arq" para cada campo da lista
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                
                //obtém o campo "arq" da lista
                int arqValue = jsonObject.getInt("arq");
                
                //cria uma nova entrada map para o campo "arq" caso ele não exista
                //na lista
                if (!arqToObjectsMap.containsKey(arqValue)) {
                    arqToObjectsMap.put(arqValue, new ArrayList<>());
                }
                
                //adiciona o objeto à lista correspondente no mapa
                arqToObjectsMap.get(arqValue).add(jsonObject);
            }


            //percorre a lista afim de ordenar o campo "ordem" da lista
            for (int arqValue : arqToObjectsMap.keySet()) {
                //obtém a lista de objetos json corresponende a o valor de "arq"
                List<JSONObject> objects = arqToObjectsMap.get(arqValue);

                //ordena a lista de objetos comn abse no campo "ordem"
                Collections.sort(objects, new Comparator<JSONObject>() {
                    
                    //compara os avalores de "ordem" dos objetos para determinar a ordem de classificação
                    @Override                    
                    public int compare(JSONObject obj1, JSONObject obj2) {
                        return Integer.compare(obj1.getInt("ordem"), obj2.getInt("ordem"));
                    }
                });

                //utilizado para concatenar as notas de todos os objetos
                StringBuilder todasAsNotas = new StringBuilder();
                for (JSONObject obj : objects) {
                    //concatena o campo "notas" de cada objeto
                    todasAsNotas.append(obj.getString("notas"));
                }

                String caminhoArquivoNotas = "C:\\Users\\manu\\Desktop\\Ordenacao\\src\\main\\java\\com\\mycompany\\ordenacao\\" + "arq_" + arqValue + ".json";

                 //escreve o arquivo de saíoda
                try (FileWriter fileWriter = new FileWriter(caminhoArquivoNotas)) {
                    //escreve as notas concatenadas no arquivo.
                    fileWriter.write(todasAsNotas.toString());
                }

                System.out.println("Notas para arq=" + arqValue + " foram escritas em " + caminhoArquivoNotas);
            }

        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
