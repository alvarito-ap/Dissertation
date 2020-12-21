package com.Alvaro.TFG.Util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Excel {
    private ArrayList<String[]> arrayDatos = new ArrayList<>();
    private ExcelColumInfo excelColumInfo;

    public Excel(MultipartFile file){
        try {
            readExcelFileToArray(file);
            excelColumInfo = new ExcelColumInfo(arrayDatos);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }
    }

    public ArrayList<String[]> getTeenagersCol(ColumType tipo){
        ArrayList<String[]> teenagers = new ArrayList<>();
        int col = excelColumInfo.basica(tipo);
        System.out.println(col);
        for (int i = 1; i < arrayDatos.size(); i++){
            String [] data = new String[1];
            if(tipo == ColumType.NAME){
                System.out.println("Separo: "+arrayDatos.get(i)[col]);
                data = arrayDatos.get(i)[col].split("\\s*,\\s*");
            }else{
                data[0] = arrayDatos.get(i)[col];
            }
            teenagers.add(data);
        }
        return teenagers;
    }

    public ArrayList<String[]> getPlacesFrec(ColumType tipo){
        ArrayList<String[]> placesInfo = new ArrayList<>();
        int col = excelColumInfo.placesFrec(tipo);
        for (int i = 1 ; i < arrayDatos.size(); i++){
            String[] info = new String[4];
            info[0] = arrayDatos.get(i)[col];info[1] = arrayDatos.get(i)[col+1];info[2] = arrayDatos.get(i)[col+2];info[3] = arrayDatos.get(i)[col+3];
            placesInfo.add(info);
        }
        return placesInfo;
    }

    public ArrayList<String[]> relationCol(ColumType tipoTiempo, ColumType tipoConsume){
        ArrayList<String[]> relationCol = new ArrayList<>();
        int col = excelColumInfo.columRelationClassmate(tipoTiempo.getStudentHeader());
        int colConsume = excelColumInfo.columRelationClassmate(tipoConsume.getStudentRelationConsume());
        for(int i=1; i<arrayDatos.size(); i++){
            String[] f = new String[2];
            f[0] = arrayDatos.get(i)[col];
            f[1] = arrayDatos.get(i)[colConsume];
            relationCol.add(f);
        }
        return relationCol;
    }

    public ArrayList<ArrayList<String>> getEgocentric (){
        int indexs [] = excelColumInfo.egocentricColums();
        System.out.println(indexs[0]+" "+indexs[1]+" "+indexs[2]);
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        for (int i = 1; i<arrayDatos.size(); i++){
            ArrayList<String> fila = new ArrayList<>();
            for(int j = indexs[0]; j<(10*3)+indexs[0]; j++){
                fila.add(arrayDatos.get(i)[j]);
            }
            for(int j = indexs[1]; j<(10*4)+indexs[1]; j++){
                fila.add(arrayDatos.get(i)[j]);
            }
            for(int j = indexs[2]; j<(10*4)+indexs[2]; j++){
                fila.add(arrayDatos.get(i)[j]);
            }
            result.add(fila);
        }
        System.out.println(result.size()+" "+result.get(0).size());
        return result;
    }

    public ArrayList<String[]> getFamilyAffluency(ColumType fam){
        ArrayList<String[]> result = new ArrayList<>();
        int col = excelColumInfo.familyAffluency(fam.toString());
        if(col>0){
            for(int i = 1; i<arrayDatos.size(); i++){
                String [] datos = new String[5];
                datos[0] = arrayDatos.get(i)[col];
                datos[1] = arrayDatos.get(i)[col+2];
                datos[2] = arrayDatos.get(i)[col+4];
                datos[3] = arrayDatos.get(i)[col+6];
                datos[4] = arrayDatos.get(i)[col+8];
                result.add(datos);
            }
        }
        return  result;
    }

    public void readExcelFileToArray(MultipartFile file){
        InputStream excelStream = null;
        try {
            File excelFile = new File(file.getOriginalFilename());
            excelFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(excelFile);
            fos.write(file.getBytes());
            fos.close();
            excelStream = new FileInputStream(excelFile);
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excelStream);
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            XSSFRow xssfRow = xssfSheet.getRow(xssfSheet.getTopRow());
            String [] datos = new String[xssfRow.getLastCellNum()];
            int cont = 0;
            for (Row row: xssfSheet) {
                System.out.println("proceso fila ="+cont++);
                int elemsRow =0;
                if(!isRowEmpty(row)){
                    for (Cell cell : row) {
                        elemsRow++;
                        datos[cell.getColumnIndex()] =
                                (cell.getCellType() == CellType.STRING)?cell.getStringCellValue():
                                        (HSSFDateUtil.isCellDateFormatted(cell))?""+new SimpleDateFormat("MM/dd/yyyy").format(cell.getDateCellValue()):
                                                (cell.getCellType() == CellType.NUMERIC)?"" + cell.getNumericCellValue():
                                                        (cell.getCellType() == CellType.BOOLEAN)?"" + cell.getBooleanCellValue():
                                                                (cell.getCellType() == CellType.BLANK)?"BLANK":
                                                                        (cell.getCellType() == CellType.FORMULA)?"FORMULA":
                                                                                (cell.getCellType() == CellType.ERROR)?"ERROR":"";
                    }
                    System.out.println("elemento fila ="+elemsRow);
                    arrayDatos.add(datos);
                }


                datos = new String[xssfRow.getLastCellNum()];
            }
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
        } catch (IOException ex) {
            System.out.println("Error in file procesing (Error al procesar el fichero): " + ex);
        } finally {
            try {
                excelStream.close();
            } catch (IOException ex) {
                System.out.println("Error in file processing after close it (Error al procesar el fichero después de cerrarlo): " + ex);
            }
        }
    }

    public static boolean isRowEmpty(Row row) {
        if(row.getLastCellNum()>0){
            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                Cell cell = row.getCell(c);
                if (cell != null && cell.getCellType() != CellType.BLANK)
                    return false;
            }
        }
        return true;
    }
}
