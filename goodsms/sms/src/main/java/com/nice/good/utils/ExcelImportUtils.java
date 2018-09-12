package com.nice.good.utils;

import com.nice.good.enums.ResultCode;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.xml.bind.JAXBIntrospector.getValue;

public class ExcelImportUtils {

	private static Logger log = LoggerFactory.getLogger(ExcelImportUtils.class);

	public int totalRows; //sheet中总行数
	public static int totalCells; //每一行总单元格数

	private static final String EXCEL_XLS = "xls";
	private static final String EXCEL_XLSX = "xlsx";
	private final static String UP_PATH = "/upload_temp/";
	private static final String PROJECT_PATH = System.getProperty("user.dir");//项目在硬盘上的基础路径
	private static final String RESOURCES_PATH = "/src/main/resources";//资源文件路径

	/**
	 * 判断：是否是2003的excel，返回true是2003
	 */
	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	/**
	 * 判断：是否是2007的excel，返回true是2007
	 */
	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	/**
	 * 验证EXCEL文件
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean validateExcel(String filePath) {
		if (filePath == null || !(isExcel2003(filePath) || isExcel2007(filePath))) {
			return false;
		}
		return true;
	}

	/**
	 * 读取excel
	 * //     * @param file
	 *
	 * @return
	 * @throws IOException
	 */
//    public List<ArrayList<String>> readExcel(MultipartFile file) throws IOException {
	public static void main(String[] args) throws IOException {

//        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 同时支持Excel 2003、2007
			File excelFile = new File("d:/考勤报表.xlsx"); // 创建文件对象
			FileInputStream in = new FileInputStream(excelFile); // 文件流
			checkExcelVaild(excelFile);//判断是否是excel文件
			Workbook workbook = getWorkbok(in, excelFile);
			//Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的

			int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			/**
			 * 设置当前excel中sheet的下标：0开始
			 */

			Sheet sheet = workbook.getSheetAt(0);   // 遍历第 一个Sheet
//            Sheet sheet = workbook.getSheetAt(2);   // 遍历第 三个Sheet

			//获取总行数
			System.out.println("总行数: " + sheet.getLastRowNum());
			Row rowForCount = sheet.getRow(0);
			//获取总列数(空格的不计算)
			int columnTotalNum = rowForCount.getPhysicalNumberOfCells();
			System.out.println("总列数：" + columnTotalNum);

			System.out.println("最大列数：" + rowForCount.getLastCellNum());

			// 为跳过第 一行目录设置count
			int count = 0;
			for (Row row : sheet) {
				try {

					int end = row.getLastCellNum();
					for (int i = 0; i < end; i++) {
						Cell cell = row.getCell(i);
						if (cell == null) {

							continue;
						}

						Object obj = getValue(cell);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//        return null;
	}

	/**
	 * 判断Excel的版本,获取Workbook
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbok(InputStream in, File file) throws IOException {
		Workbook wb = null;
		if (file.getName().endsWith(EXCEL_XLS)) {  //Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName().endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}

	/**
	 * 判断Excel的版本,获取Workbook
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbok(InputStream in, MultipartFile file) throws IOException {
		Workbook wb = null;
		if (file.getOriginalFilename().endsWith(EXCEL_XLS)) {  //Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (file.getOriginalFilename().endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}

	/**
	 * 判断文件是否是excel
	 *
	 * @throws Exception
	 */
	public static void checkExcelVaild(MultipartFile file) throws Exception {
		if (file == null || file.isEmpty()) {
			log.error(ResultCode.FILE_NOTEMPITY.getMessage());
			throw new Exception("文件不存在");
		}
		if (!(file.getOriginalFilename().endsWith(EXCEL_XLS) || file.getOriginalFilename().endsWith(EXCEL_XLSX))) {
			log.error(ResultCode.FILE_NOTEXCEL.getMessage());
			throw new Exception("文件不是Excel");
		}
	}

	/**
	 * 判断文件是否是excel
	 *
	 * @throws Exception
	 */
	public static void checkExcelVaild(File file) throws Exception {
		if (!file.exists()) {
			throw new Exception("文件不存在");
		}
		if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
			throw new Exception("文件不是Excel");
		}
	}

	//========================================= excel 解析方法 ==========================================//

	/**
	 * 读取excel文件，解析获取sheet页
	 *
	 * @param file     导入的excel文件
	 * @param sheetNum 解析的sheet页下标，指定第 几个sheet页
	 * @return
	 */
	public static Sheet getExcelSheet(MultipartFile file, int sheetNum) {
		Sheet sheet = null;
		try {
			// 同时支持Excel 2003、2007
			checkExcelVaild(file);//判断是否是excel文件
			Workbook workbook = ExcelImportUtils.getWorkbok(file.getInputStream(), file);
//            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			// 设置当前excel中sheet的下标：0开始
			sheet = workbook.getSheetAt(sheetNum);   // 遍历第 sheetNum个Sheet

			//获取总行数
			System.out.println("总行数: " + sheet.getLastRowNum());
			log.info(file.getOriginalFilename() + "的总行数: " + sheet.getLastRowNum());
			Row rowForCount = sheet.getRow(0);
			for (int i = 1; i < sheet.getLastRowNum(); i++) {
				if (rowForCount == null) {
					rowForCount = sheet.getRow(i);
				} else {
					break;
				}

			}
			//获取总列数(空格的不计算)
//            int columnTotalNum = rowForCount.getPhysicalNumberOfCells();
			System.out.println("总列数：" + rowForCount.getPhysicalNumberOfCells());
			System.out.println("最大列数：" + rowForCount.getLastCellNum());
			log.info(file.getOriginalFilename() + "的总列数：" + rowForCount.getPhysicalNumberOfCells());
			log.info(file.getOriginalFilename() + "最大列数：" + rowForCount.getLastCellNum());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("导入库位失败：" + e.getMessage().toString());
		}

		return sheet;
	}

	/**
	 * @param sheet
	 * @return
	 */
	public static List<String[]> getListFromSheet(Sheet sheet) {

		List<String[]> list = new ArrayList<>();
		// 为跳过第 一行目录设置count
		int count = 0;
		int num = 0;
		for (Row row : sheet) {
			if (row.getLastCellNum() > num) {
				num = row.getLastCellNum();
			}
			// 跳过第 一二行的目录，第 一行表头，第 二行字段名
			if (count <= 1) {
				count++;
				continue;
			}
			String[] strings = new String[num];
			for (int i = 0; i < num; i++) {
				strings[i] = String.valueOf(getValue(row.getCell(i)));
				if ("null".equals(strings[i])) {
					strings[i] = "";
				}
			}
			list.add(strings);
		}
		return list;
	}

	/**
	 * excel模板导出
	 *
	 * @param response
	 * @param request
	 * @param templeteName
	 * @param path
	 * @throws IOException
	 */
	public void dowFileUtils(HttpServletResponse response, HttpServletRequest request, String templeteName, String path) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			String filedownload = path + templeteName;
			is = this.getClass().getResourceAsStream(filedownload);
			byte[] buffer = new byte[1024];
			//对文件编码
			templeteName = response.encodeURL(new String(templeteName.getBytes("gb2312"), "iso8859-1"));
			response.reset();
			response.addHeader("Content-Disposition", "attachment; filename=" + templeteName);
			response.setContentType("application/octet-stream;charset=UTF-8");
			os = response.getOutputStream();
			int len = 0;
			while ((len = is.read(buffer)) > 0) {
				os.write(buffer, 0, len);
			}
		} catch (Exception e) {
			log.error("下载模板文件异常:{}", e);
		} finally {
			os.close();
			is.close();
		}
	}

	/**
	 * 给文件重命名
	 */
	public static String getUuName(String fileName) {
		int index = fileName.lastIndexOf(".");
		char[] ch = fileName.toCharArray();
		String lastString = String.copyValueOf(ch, index + 1, ch.length - index - 1);
		String UuName = UUID.randomUUID().toString() + "." + lastString;
		return UuName;
	}

	/**
	 * 将预览时的excel暂存到指定目录的方法
	 */
	public static void addExlce(MultipartFile file, String UuName) throws IOException {
		//获取文件名
		String path = PROJECT_PATH + RESOURCES_PATH + UP_PATH;
		//将文件暂时写入
		FileOutputStream out = new FileOutputStream(path + UuName);
		InputStream in = file.getInputStream();
		byte buffer[] = new byte[1024];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			out.write(buffer, 0, len);
		}
		out.flush();
		out.close();
	}

	/**
	 * 删除指定目录下的文件
	 *
	 * @param dir
	 * @return
	 */

	public static void deleteDir(File dir) {
		if (dir.isDirectory()) {
			//递归删除目录中的子目录下
			File[] children = dir.listFiles();
			for (File file : children) {
				file.delete();
			}
		}
	}

	/**
	 * 查找文件
	 *
	 * @param baseDirName    查找的文件夹路径
	 * @param targetFileName 需要查找的文件名
	 */
	public static MultipartFile findFiles(String baseDirName, String targetFileName) throws IOException {

		File baseDir = new File(baseDirName);       // 创建一个File对象
		if (!baseDir.exists() || !baseDir.isDirectory()) {  // 判断目录是否存在
			System.out.println("文件查找失败：" + baseDirName + "不是一个目录！");
			return null;
		}
		String tempName = null;

		File tempFile;
		File[] files = baseDir.listFiles();
		if (files.length == 0) {//该文件夹下没有文件，为空文件夹
			return null;
		}
		for (int i = 0; i < files.length; i++) {
			tempFile = files[i];
			tempName = tempFile.getName();

			if (tempName.equals(targetFileName)) {
				System.out.println(tempFile.getAbsoluteFile().toString());
				FileInputStream input = new FileInputStream(tempFile);
				MultipartFile file = new MockMultipartFile("file", tempName, "xlsx/xls", input);
				return file;
			}
		}
		return null;
	}

	public static class ExportExcelSeedBack {
		//显示导出表的标题
		private String title;
		//导出表的列名
		private String[] rowName;
		private List<Object[]> dataList = new ArrayList<>();
		HttpServletResponse response;

		//构造方法,传入要导出的数据
		public ExportExcelSeedBack(String title, String[] rowName, List<Object[]> dataList) {
			this.dataList = dataList;
			this.rowName = rowName;
			this.title = title;
		}

		/**
		 * 导出数据
		 * 2003Excel
		 */
		public void export(OutputStream out) throws Exception {
			try {
				HSSFWorkbook workbook = new HSSFWorkbook();                     // 创建工作簿对象
				HSSFSheet sheet = workbook.createSheet(title);                  // 创建工作表
				// 产生表格标题行
				//          HSSFRow rowm = sheet.createRow(0);
				//          HSSFCell cellTiltle = rowm.createCell(0);

				//sheet样式定义【getColumnTopStyle()/getStyle()均为自定义方法 - 在下面  - 可扩展】
				HSSFCellStyle columnTopStyle = getColumnTopStyle(workbook);//获取列头样式对象
				HSSFCellStyle style = getStyle(workbook);                  //单元格样式对象

				//          sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length-1)));//合并单元格
				//          cellTiltle.setCellStyle(columnTopStyle);
				//          cellTiltle.setCellValue(title);
				// 定义所需列数
				int columnNum = rowName.length;
				HSSFRow rowRowName = sheet.createRow(0);                // 在索引2的位置创建行(最顶端的行开始的第 二行)
				// 将列头设置到sheet的单元格中
				for (int n = 0; n < columnNum; n++) {
					HSSFCell cellRowName = rowRowName.createCell(n);               //创建列头对应个数的单元格
					cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型
					HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
					cellRowName.setCellValue(text);                                 //设置列头单元格的值
					cellRowName.setCellStyle(columnTopStyle);                       //设置列头单元格样式
				}
				//将查询出的数据设置到sheet对应的单元格中
				for (int i = 0; i < dataList.size(); i++) {

					Object[] obj = dataList.get(i);//遍历每个对象
					HSSFRow row = sheet.createRow(i + 1);//创建所需的行数（从第 二行开始写数据）

					for (int j = 0; j < obj.length; j++) {
						HSSFCell cell = null;   //设置单元格的数据类型
//                        if (j == 0) {
//                            cell = row.createCell(j, HSSFCell.CELL_TYPE_NUMERIC);
//                            cell.setCellValue(i + 1);
//                            cell.setCellValue(obj[j].toString());
//                        } else {
						cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
						if (!"".equals(obj[j]) && obj[j] != null) {
							cell.setCellValue(obj[j].toString());                       //设置单元格的值
						}
//                        }
						cell.setCellStyle(style);                                   //设置单元格样式
					}
				}
				//让列宽随着导出的列长自动适应
				for (int colNum = 0; colNum < columnNum; colNum++) {
					int columnWidth = sheet.getColumnWidth(colNum) / 256;
					for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
						HSSFRow currentRow;
						//当前行未被使用过
						if (sheet.getRow(rowNum) == null) {
							currentRow = sheet.createRow(rowNum);
						} else {
							currentRow = sheet.getRow(rowNum);
						}
						//                 if (currentRow.getCell(colNum) != null) {
						//                     HSSFCell currentCell = currentRow.getCell(colNum);
						//                      if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
						//                          int length =     currentCell.getStringCellValue().getBytes().length;
						//                          if (columnWidth < length) {
						//                              columnWidth = length;
						//                          }
						//                      }
						//                  }

						//此段自动填充列宽高,放开如果表格数据位空会抛异常,日后解决
						/*if(currentRow.getCell(colNum).toString().length()>0) {
                            if (currentRow.getCell(colNum) != null && (!currentRow.getCell(colNum).equals(""))) {
                                HSSFCell currentCell = currentRow.getCell(colNum);
                                if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                    int length = 0;
                                    try {
                                        if (null != currentCell && null != currentCell.getStringCellValue()) {
                                            length = currentCell.getStringCellValue().getBytes().length;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (columnWidth < length) {
                                        columnWidth = length;
                                    }
                                }
                            }
                        }*/
					}
					if (colNum == 0) {
						sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
					} else {
						sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
					}
				}
				if (workbook != null) {
					try {
						workbook.write(out);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				out.close();
			}

		}
	}

	/**
	 * 列数据信息单元格样式
	 */
	public static HSSFCellStyle getStyle(HSSFWorkbook workbook) {
		// 设置字体
		HSSFFont font = workbook.createFont();
		//设置字体大小
		//font.setFontHeightInPoints((short)10);
		//字体加粗
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//设置字体名字
		font.setFontName("Courier New");
		//设置样式;
		HSSFCellStyle style = workbook.createCellStyle();
		//设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		//设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		//设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		//设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		//设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		//设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		//在样式用应用设置的字体;
		style.setFont(font);
		//设置自动换行;
		style.setWrapText(false);
		//设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;


	}

	/**
	 * ;列头单元格样式
	 */
	public static HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
		//设置字体
		HSSFFont font = workbook.createFont();
		//设置字体大小
		font.setFontHeightInPoints((short) 11);
		//字体加粗
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//设置字体名字
		font.setFontName("Courier New");
		//设置样式
		HSSFCellStyle style = workbook.createCellStyle();
		//设置底边框;
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		//设置底边框颜色;
		style.setBottomBorderColor(HSSFColor.BLACK.index);
		//设置左边框;
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		//设置左边框颜色;
		style.setLeftBorderColor(HSSFColor.BLACK.index);
		//设置右边框;
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		//设置右边框颜色;
		style.setRightBorderColor(HSSFColor.BLACK.index);
		//设置顶边框;
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		//设置顶边框颜色;
		style.setTopBorderColor(HSSFColor.BLACK.index);
		//在样式用应用设置的字体;
		style.setFont(font);
		//设置自动换行;
		style.setWrapText(false);
		//设置水平对齐的样式为居中对齐;
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置垂直对齐的样式为居中对齐;
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		return style;
	}
}  
