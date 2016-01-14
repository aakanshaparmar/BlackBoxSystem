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

    /**
     * This method gets the <code>ElderlyLocationInfo</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>ElderlyLocationInfo</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getElderlyLocationInfo")
    public ElderlyLocationInfo getElderlyLocationInfo(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getElderlyLocationInfo method");
        return null;
    }

    /**
     * This inserts a new <code>ElderlyLocationInfo</code> object.
     *
     * @param eldLocInfo The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertElderlyLocationInfo")
    public ElderlyLocationInfo insertElderlyLocationInfo(ElderlyLocationInfo eldLocInfo) {
        // TODO: Implement this function
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

        try {
            Connection conn = DriverManager.getConnection(url);
            try {
                String statement1 = "USE bbsystemDB";
                PreparedStatement stmt1 = conn.prepareStatement(statement1);
                stmt1.executeUpdate();

                //String statement = "SELECT * FROM elderlyLocInfo";
                //PreparedStatement stmt = conn.prepareStatement(statement);

                /*stmt.setFloat(1, eldLocInfo.getLatitude());
                stmt.setFloat(2, eldLocInfo.getLongitude());
                stmt.setString(3, eldLocInfo.getLocID());
                stmt.setString(4, eldLocInfo.getEldID());
                stmt.setDate(5, eldLocInfo.getLocDate());*/

                int success;
                success = 1;
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

        return eldLocInfo;
    }
}