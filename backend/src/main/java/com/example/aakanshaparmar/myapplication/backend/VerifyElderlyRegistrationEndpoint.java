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
        name = "verifyElderlyRegistrationApi",
        version = "v1",
        resource = "verifyElderlyRegistration",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.aakanshaparmar.example.com",
                ownerName = "backend.myapplication.aakanshaparmar.example.com",
                packagePath = ""
        )
)
public class VerifyElderlyRegistrationEndpoint {

    private static final Logger logger = Logger.getLogger(VerifyElderlyRegistrationEndpoint.class.getName());

    public VerifyElderlyRegistrationEndpoint() {
    }

    @ApiMethod(name = "getVerifyElderlyRegistration")
    public VerifyElderlyRegistration getVerifyElderlyRegistration(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getVerifyElderlyRegistration method");
        return null;
    }


    @ApiMethod(name = "insertVerifyElderlyRegistration")
    public VerifyElderlyRegistration insertVerifyElderlyRegistration(VerifyElderlyRegistration checkEldInfo) {
        // TODO: Implement this function
        logger.info("Calling insertVerifyElderlyRegistration method");

        // TODO: Implement this function
        logger.info("Calling insertcheckElderlyRegistration method");

        logger.info("Calling insertElderlyRegistration method");

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

                ResultSet numberOfRecords = conn.createStatement().executeQuery("select count(*) from elderlyInfo where phoneNo = \"" + checkEldInfo.getPhoneNo() + "\"");
                checkEldInfo.setPhoneNo(String.valueOf(numberOfRecords));
                conn.commit();
            }finally{
                conn.close();
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return null;
        }

        return checkEldInfo;

    }
}