package com.yourorganization.maven_sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.printer.DotPrinter;
import com.github.javaparser.printer.XmlPrinter;
import com.github.javaparser.printer.YamlPrinter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.Log;
import com.github.javaparser.utils.SourceRoot;
import com.google.gson.*;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.yaml.snakeyaml.Yaml;


import java.io.*;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Some code that uses JavaParser.
 */
public class LogicPositivizer {
    public static void main(String[] args) throws IOException {
        // Parse the code you want to inspect:
        File file = new File("/home/nakhla/deepRL/time_1_buggy/src/main/java/org/joda/time/Partial.java");
        CompilationUnit cu = StaticJavaParser.parse(file);
        // Now comes the inspection code:
        //System.out.println(cu);

        // Now comes the inspection code:
        YamlPrinter printer2 = new YamlPrinter(true);
        String yFile = printer2.output(cu);

        PrintWriter out = new PrintWriter("output.yaml");
        out.println(yFile);

        // Now comes the inspection code:
        XmlPrinter printer3 = new XmlPrinter(true);
        //System.out.println(printer3.output(cu).getClass().getName());

        String s = convertYamlToJson(printer2.output(cu));

        try {
            JSONObject jsonObject = new JSONObject(s);


            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement je = JsonParser.parseString(s);
            String prettyJsonString = gson.toJson(je);
            FileWriter file4 = new FileWriter("output1.json");
            file4.write(prettyJsonString);
            file4.close();
            System.out.println(prettyJsonString);

        }catch (JSONException err){

        }




        Gson gson = new Gson();
        //String json = "{\"name\":\"john\",\"age\":22,\"class\":\"mca\"}";

        //System.out.println("original: "+json);

        String escaped = gson.toJson(s);
        //System.out.println("escaped: "+escaped);
//        // JavaParser has a minimal logging class that normally logs nothing.
//        // Let's ask it to write to standard out:
//        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
//
//        // SourceRoot is a tool that read and writes Java files from packages on a certain root directory.
//        // In this case the root directory is found by taking the root from the current Maven module,
//        // with src/main/resources appended.
//        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class).resolve("src/main/resources"));
//
//        // Our sample is in the root of this directory, so no package name.
//        CompilationUnit cu = sourceRoot.parse("", "Blabla.java");
//
//        Log.info("Positivizing!");
//
//        cu.accept(new ModifierVisitor<Void>() {
//            /**
//             * For every if-statement, see if it has a comparison using "!=".
//             * Change it to "==" and switch the "then" and "else" statements around.
//             */
//            @Override
//            public Visitable visit(IfStmt n, Void arg) {
//                // Figure out what to get and what to cast simply by looking at the AST in a debugger!
//                n.getCondition().ifBinaryExpr(binaryExpr -> {
//                    if (binaryExpr.getOperator() == BinaryExpr.Operator.NOT_EQUALS && n.getElseStmt().isPresent()) {
//                        /* It's a good idea to clone nodes that you move around.
//                            JavaParser (or you) might get confused about who their parent is!
//                        */
//                        Statement thenStmt = n.getThenStmt().clone();
//                        Statement elseStmt = n.getElseStmt().get().clone();
//                        n.setThenStmt(elseStmt);
//                        n.setElseStmt(thenStmt);
//                        binaryExpr.setOperator(BinaryExpr.Operator.EQUALS);
//                    }
//                });
//                return super.visit(n, arg);
//            }
//        }, null);
//
//        // This saves all the files we just read to an output directory.
//        sourceRoot.saveAll(
//                // The path of the Maven module/project which contains the LogicPositivizer class.
//                CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class)
//                        // appended with a path to "output"
//                        .resolve(Paths.get("output")));
    }

    private static String convertYamlToJson(String yaml) throws IOException {
        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = yamlReader.readValue(yaml, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writeValueAsString(obj);
    }

}
