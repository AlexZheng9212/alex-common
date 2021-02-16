package com.alex.common2.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

public class CellUtils {
  public static String genCellValue(Cell cell) {
    CellType cellType = cell.getCellType();
    switch (cellType) {
      case NUMERIC:
        return cell.getStringCellValue();
      case STRING:
        return cell.getStringCellValue();
      case BOOLEAN:
        return String.valueOf(cell.getBooleanCellValue());
      case FORMULA:
        return cell.getCellFormula();
      case BLANK:
        return StringUtils.EMPTY;
      case ERROR:
        return String.valueOf(cell.getErrorCellValue());
      default:
        return StringUtils.EMPTY;
    }
  }
}