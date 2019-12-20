package opencsvbuilder;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class OpenCSVBuilder<E> implements ICSVBuilder {

    @Override
    public Iterator<E> getCsvFileIterator(Reader reader, Class csvClass) throws CSVBuilderException {
        try {
            return this.getCSVBean(reader,csvClass).iterator();
        }catch (RuntimeException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }

    @Override
    public List getCsvFileList(Reader reader, Class csvClass) throws CSVBuilderException {
       try {
           return this.getCSVBean(reader,csvClass).parse();
       }catch (RuntimeException e){
           throw new CSVBuilderException(e.getMessage(),
                   CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
       }
    }

    private CsvToBean<E> getCSVBean(Reader reader, Class csvClass) throws CSVBuilderException {
        try{
            CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(csvClass);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            return csvToBeanBuilder.build();
        }catch (IllegalStateException e){
            throw new CSVBuilderException(e.getMessage(),
                    CSVBuilderException.ExceptionType.UNABLE_TO_PARSE);
        }
    }
}
