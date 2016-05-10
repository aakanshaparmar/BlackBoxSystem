package com.example.aakanshaparmar.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.utils.SystemProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "elderlyLocationInfoApi",
        version = "v1",
        resource = "elderlyLocationInfo",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.aakanshaparmar.example.com",
                ownerName = "backend.myapplication.aakanshaparmar.example.com",
                packagePath = ""
        )
)
public class ElderlyLocationInfoEndpoint {

    private static final Logger logger = Logger.getLogger(ElderlyLocationInfoEndpoint.class.getName());

    @ApiMethod(name = "insertElderlyLocationInfo")
    public ElderlyLocationInfo insertElderlyLocationInfo(ElderlyLocationInfo eldLocInfo) {

        logger.info("Calling insertElderlyLocationInfo method");

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

                String statement = "INSERT INTO elderlyLocInfo (latitude, longitude, locID, eID) VALUES ( ? , ? , ? , ?)";
                PreparedStatement stmtMoney = conn.prepareStatement(statement);
                stmtMoney.setFloat(1, (float) 22.2840);
                stmtMoney.setFloat(2, (float) 114.1350);
                stmtMoney.setString(3, "le12341");
                stmtMoney.setString(4, "e789564 ");
                stmtMoney.executeUpdate();


                /*int success;
                success = 1;
                if (success == 1) {
                    logger.info("Success in uploading to Cloud SQL");
                } else if (success == 0) {
                    logger.warning("Failure in uploading to Cloud SQL");
                    conn.close();
                    return null;
                }*/

                conn.commit();
            }finally{
                conn.close();
            }
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return null;
        }

        return eldLocInfo;
    }
}