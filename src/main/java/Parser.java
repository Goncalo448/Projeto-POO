import CasaInteligente.CasaInteligente;
import CasaInteligente.SmartDevices.SmartBulb;
import CasaInteligente.SmartDevices.SmartCamera;
import CasaInteligente.SmartDevices.SmartSpeaker;
import ComercializadoresEnergia.Comercializador;

import static java.lang.System.out;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {
        public static void main(String[] args){
            Comunidade comunidade = new Comunidade("Rumo ao 20");
            List<String> linhas = lerFicheiro("dados.txt");
            int id_generator = 0;

            String[] linhaPartida;
            String divisaoMaisRecente = null;
            CasaInteligente casaMaisRecente = null;
            for (String linha : linhas) {
                linhaPartida = linha.split(":", 2);
                String[] campos = linhaPartida[1].split(",");
                switch (linhaPartida[0]) {
                    case "Fornecedor" -> {
                        String nomeEmpresa = linhaPartida[1];
                        Comercializador comercializador = new Comercializador(nomeEmpresa);
                        comunidade.setMercado(nomeEmpresa, comercializador);
                    }
                    case "Casa" -> {
                        casaMaisRecente = parseCasa(campos).clone();
                        String proprietario = campos[0];
                        comunidade.setCasas(proprietario, casaMaisRecente);
                    }
                    case "Divisao" -> {
                        if (casaMaisRecente == null) System.out.println("Linha inválida.");
                        divisaoMaisRecente = linhaPartida[1];
                        assert casaMaisRecente != null;
                        comunidade.getCasa(casaMaisRecente.getProprietario()).addRoom(divisaoMaisRecente);
                    }
                    case "SmartBulb" -> {
                        if (divisaoMaisRecente == null) System.out.println("Linha inválida.");
                        SmartBulb sd = parseSmartBulb(campos, Integer.toString(id_generator));
                        assert casaMaisRecente != null;
                        comunidade.getCasa(casaMaisRecente.getProprietario()).addDevice(sd, divisaoMaisRecente);
                        id_generator += 1;
                    }
                    case "SmartCamera" -> {
                        if (divisaoMaisRecente == null) System.out.println("Linha inválida.");
                        SmartCamera sc = parseSmartCamera(campos, Integer.toString(id_generator));
                        assert casaMaisRecente != null;
                        comunidade.getCasa(casaMaisRecente.getProprietario()).addDevice(sc, divisaoMaisRecente);
                        id_generator += 1;
                    }
                    case "SmartSpeaker" -> {
                        if (divisaoMaisRecente == null) System.out.println("Linha inválida.");
                        SmartSpeaker sp = parseSmartSpeaker(campos, Integer.toString(id_generator));
                        assert casaMaisRecente != null;
                        comunidade.getCasa(casaMaisRecente.getProprietario()).addDevice(sp, divisaoMaisRecente);
                        id_generator += 1;
                    }
                    default -> System.out.println("Linha inválida.");
                }
            }

            out.println("Done!");
            out.println(comunidade);

        }

    public static List<String> lerFicheiro(String nomeFich) {
        List<String> lines;
        try { lines = Files.readAllLines(Paths.get(nomeFich), StandardCharsets.UTF_8); }
        catch(IOException exc) { lines = new ArrayList<>(); }
        return lines;
    }

    public static SmartBulb parseSmartBulb(String[] s, String id) {
        int tone = switch (s[0]) {
            case "Warm" -> SmartBulb.WARM;
            case "Neutral" -> SmartBulb.NEUTRAL;
            case "Cold" -> SmartBulb.COLD;
            default -> 0;
        };

        if (tone == 0) System.out.println("Linha inválida.");

        int diametro = Integer.parseInt(s[1]);
        float consumo = Float.parseFloat(s[2]);

        SmartBulb new_bulb = new SmartBulb(id, false, tone, diametro, consumo, 5);
        return new_bulb.clone();
    }

    public static SmartCamera parseSmartCamera(String[] campos, String id){

            String[] splitByRightParenthesis = campos[0].split("x", 2);
            String xRes = splitByRightParenthesis[0].substring(1);
            String yRes = splitByRightParenthesis[1].substring(0, splitByRightParenthesis[1].length() -1);

            int tamanho = Integer.parseInt(campos[1]);
            float consumo = Float.parseFloat(campos[2]);

            SmartCamera new_camera = new SmartCamera(id, false, Integer.parseInt(xRes), Integer.parseInt(yRes), tamanho, consumo, 10);
            return new_camera.clone();
    }

    public static SmartSpeaker parseSmartSpeaker(String[] campos, String id) {
            int volume = Integer.parseInt(campos[0]);
            String channel = campos[1];
            String brand = campos[2];
            float consumption = Float.parseFloat(campos[3]);

            SmartSpeaker new_speaker = new SmartSpeaker(id, false, channel, volume, brand, consumption, 7);
            return new_speaker.clone();
    }
    public static CasaInteligente parseCasa(String[] input){
            String nome = input[0];
            long NIF = Long.parseLong(input[1]);
            String comercializador = input[2];

            return new CasaInteligente(nome, NIF, comercializador);
        }


}