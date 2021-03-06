package com.example.aakanshaparmar.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.utils.SystemProperty;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "elderlyRegistrationApi",
        version = "v1",
        resource = "elderlyRegistration",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.aakanshaparmar.example.com",
                ownerName = "backend.myapplication.aakanshaparmar.example.com",
                packagePath = ""
        )
)
public class ElderlyRegistrationEndpoint {

    private static final Logger logger = Logger.getLogger(ElderlyRegistrationEndpoint.class.getName());

    public ElderlyRegistrationEndpoint() {
    }

    @ApiMethod(name = "getElderlyRegistration")
    public ElderlyRegistration getElderlyRegistration(@Named("id") Long id) {
        logger.info("Calling getElderlyRegistration method");
        return null;
    }


    @ApiMethod(name = "insertElderlyRegistration")
    public ElderlyRegistration insertElderlyRegistration(ElderlyRegistration eldInfo) throws IOException {
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

                String statement = "INSERT INTO elderlyInfo (fullName, phoneNo, address, eID, commonPass, emergencyPhoneNo) VALUES ( ? , ? , ? , ?, ?, ?)";
                PreparedStatement stmtMoney = conn.prepareStatement(statement);
                stmtMoney.setString(1, eldInfo.getFullName());
                stmtMoney.setString(2, eldInfo.getPhoneNo());
                stmtMoney.setString(3, eldInfo.getAddress());
                stmtMoney.setString(4, eldInfo.getEID());
                stmtMoney.setInt(5, eldInfo.getCommonPass());
                stmtMoney.setString(6, eldInfo.getEmerPhoneNo());
                stmtMoney.executeUpdate();

                conn.commit();
            }finally{
                conn.close();
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return null;
        }

        return eldInfo;

    }


}