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
        name = "familyRegistrationApi",
        version = "v1",
        resource = "familyRegistration",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.aakanshaparmar.example.com",
                ownerName = "backend.myapplication.aakanshaparmar.example.com",
                packagePath = ""
        )
)
public class FamilyRegistrationEndpoint {

    private static final Logger logger = Logger.getLogger(FamilyRegistrationEndpoint.class.getName());

    /**
     * This method gets the <code>FamilyRegistration</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>FamilyRegistration</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getFamilyRegistration")
    public FamilyRegistration getFamilyRegistration(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getFamilyRegistration method");
        return null;
    }

    /**
     * This inserts a new <code>FamilyRegistration</code> object.
     *
     * @param familyInfo The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertFamilyRegistration")
    public FamilyRegistration insertFamilyRegistration(FamilyRegistration familyInfo) {
        // TODO: Implement this function
        logger.info("Calling insertFamilyRegistration method");

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

                String statement = "INSERT INTO elderlyInfo VALUES( ? , ? , ? , ? , ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(statement);

                stmt.setString(1, familyInfo.getFullName());
                stmt.setString(2, familyInfo.getPhoneNo());
                stmt.setString(3, familyInfo.getAddress());
                stmt.setString(4, familyInfo.getEID());
                stmt.setString(5, familyInfo.getEmailID());
                stmt.setString(6, familyInfo.getFID());
                stmt.setInt(7, familyInfo.getCommonPass());

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

        return familyInfo;

    }
}