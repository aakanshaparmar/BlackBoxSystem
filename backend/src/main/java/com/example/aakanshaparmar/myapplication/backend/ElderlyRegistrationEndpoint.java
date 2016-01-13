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
        // TODO: Implement this function
        logger.info("Calling getElderlyRegistration method");
        return null;
    }


    @ApiMethod(name = "insertElderlyRegistration")
    public ElderlyRegistration insertElderlyRegistration(ElderlyRegistration eldInfo) throws IOException {
        // TODO: Implement this function
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

        try {
            Connection conn = DriverManager.getConnection(url);
          try {
              String statement1 = "USE bbsystemDB";
              PreparedStatement stmt1 = conn.prepareStatement(statement1);
              stmt1.executeUpdate();

              String statement = "INSERT INTO elderlyInfo VALUES( ? , ? , ? , ? , ?)";
              PreparedStatement stmt = conn.prepareStatement(statement);

              stmt.setString(1, eldInfo.getFullName());
              stmt.setString(2, eldInfo.getPhoneNo());
              stmt.setString(3, eldInfo.getAddress());
              stmt.setString(4, eldInfo.getEID());
              stmt.setInt(5, eldInfo.getCommonPass());

              int success;
              success = stmt.executeUpdate();
              if (success == 1) {
                  logger.info("Success in uploading to Cloud SQL");
              } else if (success == 0) {
                  logger.warning("Failure in uploading to Cloud SQL");
                  conn.close();
                  return null;
              }
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