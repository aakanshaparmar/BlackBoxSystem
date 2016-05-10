package com.example.aakanshaparmar.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.utils.SystemProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.inject.Named;

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

    public ElderlyLocationInfoEndpoint() {
    }

    @ApiMethod(name = "getElderlyLocationInfo")
    public ElderlyLocationInfo getElderlyLocationInfo(@Named("id") String eID) {
        logger.info("Calling getElderlyRegistration method");

        ElderlyLocationInfo eldLocInfo = new ElderlyLocationInfo();

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

                ResultSet numberOfRecords = conn.createStatement().executeQuery("select latitude,longitude,locNo from elderlyLocInfo where eID = \"" + eID + "\";");

                if(numberOfRecords.next()){
                    eldLocInfo.setLatitude(numberOfRecords.getFloat("latitude"));
                    eldLocInfo.setLongitude(numberOfRecords.getFloat("longitude"));
                    eldLocInfo.setLocID(numberOfRecords.getInt("locNo"));
                }
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

                String statement = "INSERT INTO elderlyLocInfo (latitude, longitude, eID, locNo) VALUES ( ? , ? , ? , ?)";
                PreparedStatement stmtMoney = conn.prepareStatement(statement);
                stmtMoney.setFloat(1, eldLocInfo.getLatitude());
                stmtMoney.setFloat(2, eldLocInfo.getLongitude());
                stmtMoney.setString(3, eldLocInfo.getEldID());
                stmtMoney.setInt(4, eldLocInfo.getLocID());
                stmtMoney.executeUpdate();

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