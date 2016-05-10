package com.example.aakanshaparmar.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.utils.SystemProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "verifyFamilyElderlyNumberApi",
        version = "v1",
        resource = "verifyFamilyElderlyNumber",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.aakanshaparmar.example.com",
                ownerName = "backend.myapplication.aakanshaparmar.example.com",
                packagePath = ""
        )
)
public class VerifyFamilyElderlyNumberEndpoint {

    private static final Logger logger = Logger.getLogger(VerifyFamilyElderlyNumberEndpoint.class.getName());


    @ApiMethod(name = "getVerifyFamilyElderlyNumber")
    public VerifyFamilyElderlyNumber getVerifyFamilyElderlyNumber(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getVerifyFamilyElderlyNumber method");
        return null;
    }


    @ApiMethod(name = "insertVerifyFamilyElderlyNumber")
    public VerifyFamilyElderlyNumber insertVerifyFamilyElderlyNumber(VerifyFamilyElderlyNumber checkFamInfo) {
        // TODO: Implement this function
        logger.info("Calling insertVerifyFamilyElderlyNumber method");


        //Connect to SQL
        String url = null;
        try {
            if (SystemProperty.environment.value() ==
                    SystemProperty.Environment.Value.Production) {
                Class.forName("com.mysql.jdbc.GoogleDriver");
                url = "jdbc:google:mysql://bbsystemproject:blackboxsystemsqlinstance?user=root";

            }
        }catch(Exception e){
            logger.warning(e.getMessage());
            return null;
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

            try {
                conn.setAutoCommit(false);

                conn.createStatement().executeQuery("USE bbsystemDB;");

                ResultSet numberOfRecords = conn.createStatement().executeQuery("select count(*) AS totalNumber from elderlyInfo where phoneNo = \"" + checkFamInfo.getPhoneNo() + "\";");

                if(numberOfRecords.next()){
                    checkFamInfo.setPhoneNo(numberOfRecords.getString("totalNumber"));
                    logger.info(numberOfRecords.getString("totalNumber"));
                }
                conn.commit();
            }finally{
                conn.close();
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return null;
        }

        return checkFamInfo;
    }
}