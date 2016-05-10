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
        name = "verifyFamilyRegistrationApi",
        version = "v1",
        resource = "verifyFamilyRegistration",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.aakanshaparmar.example.com",
                ownerName = "backend.myapplication.aakanshaparmar.example.com",
                packagePath = ""
        )
)
public class VerifyFamilyRegistrationEndpoint {

    private static final Logger logger = Logger.getLogger(VerifyFamilyRegistrationEndpoint.class.getName());

    @ApiMethod(name = "getVerifyFamilyRegistration")
    public VerifyFamilyRegistration getVerifyFamilyRegistration(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getVerifyFamilyRegistration method");
        return null;
    }

    @ApiMethod(name = "insertVerifyFamilyRegistration")
    public VerifyFamilyRegistration insertVerifyFamilyRegistration(VerifyFamilyRegistration checkFamInfo) {
        // TODO: Implement this function
        logger.info("Calling insertVerifyFamilyRegistration method");


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

                ResultSet numberOfRecords = conn.createStatement().executeQuery("select count(*) AS totalNumber from familyInfo where phoneNo = \"" + checkFamInfo.getPhoneNo() + "\";");

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