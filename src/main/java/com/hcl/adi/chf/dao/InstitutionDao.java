package com.hcl.adi.chf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hcl.adi.chf.enums.Actions;
import com.hcl.adi.chf.model.CompliancePolicy;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.DataArchivalPolicy;
import com.hcl.adi.chf.model.Institution;
import com.hcl.adi.chf.model.PasswordPolicy;
import com.hcl.adi.chf.model.TermsAndConditions;
import com.hcl.adi.chf.util.Constants;
import com.hcl.adi.chf.util.DBErrorMessages;
import com.hcl.adi.chf.util.SQLUtils;
import com.hcl.adi.chf.util.TableConstants;

/**
 * This class is to perform DB Operations on institution table
 *
 * @author AyushRa
 */
public final class InstitutionDao {
	private static final Logger LOGGER = LogManager.getLogger(InstitutionDao.class.getName());

	private InstitutionDao() {
		// private constructor
	}

	/**
	 * Fetch institution list from institution table
	 *
	 * @return
	 */
	public static List<Institution> fetchInstitutionList() {
		List<Institution> institutionList = new ArrayList<Institution>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_INSTITUTION"));
			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Institution institution = new Institution();
					institution.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
					institution.setInstitutionName(rs.getString(TableConstants.INSTITUTION_NAME));
					institution.setAddress(rs.getString(TableConstants.INSTITUTION_ADDRESS));
					institution.setContactPerson(rs.getString(TableConstants.INSTITUTION_CONTACT_PERSON));
					institution.setContactNumber(rs.getString(TableConstants.INSTITUTION_CONTACT_NUMBER));
					institution.setInstitutionStatus(rs.getString(TableConstants.INSTITUTION_STATUS));

					if (Constants.INSTITUTION_STATUS_ACTIVE
							.equalsIgnoreCase(rs.getString(TableConstants.INSTITUTION_STATUS))) {
						institution.setStatusFlag(true);
					} else {
						institution.setStatusFlag(false);
					}

					institution.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_DELETE_MARKER));
					institution.setPwdExpireInDays(rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRE_IN_DAYS));
					institution.setPwdExpiryNotificationStartInDays(
							rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS));
					institution.setCreatedBy(rs.getString(TableConstants.INSTITUTION_CREATED_BY));
					institution.setCreatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_CREATED_TIMESTAMP));
					institution.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_UPDATED_BY));
					institution.setUpdatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_UPDATED_TIMESTAMP));

					institutionList.add(institution);
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institutionList;
	}

	/**
	 * Fetch list of all activated institution from institution table
	 *
	 * @return
	 */
	public static List<Institution> fetchAllActivatedInstitutionList() {
		List<Institution> institutionList = new ArrayList<Institution>();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection
					.prepareStatement(SQLUtils.getSQLQuery("SELECT_ALL_ACTIVATED_INSTITUTION_FROM_INSTITUTION"));
			ResultSet rs = pstmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Institution institution = new Institution();
					institution.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
					institution.setInstitutionName(rs.getString(TableConstants.INSTITUTION_NAME));
					institution.setAddress(rs.getString(TableConstants.INSTITUTION_ADDRESS));
					institution.setContactPerson(rs.getString(TableConstants.INSTITUTION_CONTACT_PERSON));
					institution.setContactNumber(rs.getString(TableConstants.INSTITUTION_CONTACT_NUMBER));
					institution.setInstitutionStatus(rs.getString(TableConstants.INSTITUTION_STATUS));
					institution.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_DELETE_MARKER));
					institution.setPwdExpireInDays(rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRE_IN_DAYS));
					institution.setPwdExpiryNotificationStartInDays(
							rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS));
					institution.setCreatedBy(rs.getString(TableConstants.INSTITUTION_CREATED_BY));
					institution.setCreatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_CREATED_TIMESTAMP));
					institution.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_UPDATED_BY));
					institution.setUpdatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_UPDATED_TIMESTAMP));

					institutionList.add(institution);
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institutionList;
	}

	/**
	 * Fetch an institution, its pwd policy, its data archival policy and its
	 * terms and condititons from institution, pwd_policy_mgmt,
	 * data_archival_policy_mgmt and terms_n_conditions table based on
	 * institution id
	 *
	 * @param institutionId
	 * @return
	 */
	public static Institution fetchInstitutionDetailsBasedOnInstitutionID(final int institutionId) {
		Institution institution = new Institution();

		try (Connection connection = DBConnection.getConnection();) {
			PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery(
					"SELECT_INSTITUTION_ITS_PWD_POLICY_ITS_DATA_ARCHIVAL_POLICY_AND_ITS_TNC_BY_INSTITUTION_ID"));

			pstmt.setInt(1, institutionId);

			ResultSet rs = pstmt.executeQuery();

			if (rs != null && rs.next()) {
				institution.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
				institution.setInstitutionName(rs.getString(TableConstants.INSTITUTION_NAME));
				institution.setAddress(rs.getString(TableConstants.INSTITUTION_ADDRESS));
				institution.setContactPerson(rs.getString(TableConstants.INSTITUTION_CONTACT_PERSON));
				institution.setContactNumber(rs.getString(TableConstants.INSTITUTION_CONTACT_NUMBER));
				institution.setInstitutionStatus(rs.getString(TableConstants.INSTITUTION_STATUS));

				if (Constants.INSTITUTION_STATUS_ACTIVE
						.equalsIgnoreCase(rs.getString(TableConstants.INSTITUTION_STATUS))) {
					institution.setStatusFlag(true);
				} else {
					institution.setStatusFlag(false);
				}

				institution.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_DELETE_MARKER));
				institution.setPwdExpireInDays(rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRE_IN_DAYS));
				institution.setPwdExpiryNotificationStartInDays(
						rs.getInt(TableConstants.INSTITUTION_PWD_EXPIRY_NOTIFICATION_START_IN_DAYS));
				institution.setCreatedBy(rs.getString(TableConstants.INSTITUTION_CREATED_BY));
				institution.setCreatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_CREATED_TIMESTAMP));
				institution.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_UPDATED_BY));
				institution.setUpdatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_UPDATED_TIMESTAMP));

				institution.setPwdPolicy(extractPwdPolicyFromResultSet(rs));
				institution.setTnc(extractTermsNConditionsFromResultSet(rs));
				institution.setDataArchivalPolicy(extractDataArchivalPolicyFromResultSet(rs));
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
		}

		return institution;
	}

	/**
	 * Update institute in institution table. In case of activate, deactivate
	 * and delete operation, update corresponding admins & clinicians as well
	 *
	 * @param institution
	 * @param action
	 * @return
	 */
	public static CustomResponse updateInstitution(final Institution institution, final String action) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				connection.setAutoCommit(false);

				PreparedStatement pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("UPDATE_INSTITUTION_ON_ID"));
				pstmt.setString(1, institution.getInstitutionName());
				pstmt.setString(2, institution.getAddress());
				pstmt.setString(3, institution.getContactPerson());
				pstmt.setString(4, institution.getContactNumber());
				pstmt.setString(5, institution.getInstitutionStatus());
				pstmt.setString(6, institution.getDeleteMarker());
				pstmt.setString(7, institution.getUpdatedBy());
				pstmt.setInt(8, institution.getInstitutionId());

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());

					if (Actions.DEACTIVATE.name().equalsIgnoreCase(action)
							|| Actions.ACTIVATE.name().equalsIgnoreCase(action)
							|| Actions.DELETE.name().equalsIgnoreCase(action)) {

						// Now, update admins as well based on institution id.
						CustomResponse adminResponse = AdminDao.updateAdminOnInstitutionId(institution.getUpdatedBy(),
								institution.getInstitutionId(), action);

						if (adminResponse.getStatusCode() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
							response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
							response.setDescription(
									Status.INTERNAL_SERVER_ERROR.toString() + ": " + adminResponse.getDescription());
						}

					} else {
						connection.setAutoCommit(true);
					}
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
				response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

				if (connection != null && !connection.isClosed()) {
					connection.rollback();
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * Update pwd_expire_in_days in institution table when someone successfully
	 * change the password policy for an institution in pwd_policy_mgmt table
	 *
	 * @param pwd_expire_in_days
	 * @param updatedBy
	 * @param institutionId
	 * @return
	 */
	public static CustomResponse updateInstitutionForPwd_expire_in_days(final int pwd_expire_in_days,
			final String updatedBy, final int institutionId) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsUpdated = 0;

		try (Connection connection = DBConnection.getConnection();) {
			try {
				PreparedStatement pstmt = connection
						.prepareStatement(SQLUtils.getSQLQuery("UPDATE_INSTITUTION_FOR_PWD_EXPIRE_IN_DAYS"));
				pstmt.setInt(1, pwd_expire_in_days);
				pstmt.setString(2, updatedBy);
				pstmt.setInt(3, institutionId);

				numberOfRecordsUpdated = pstmt.executeUpdate();

				if (numberOfRecordsUpdated > 0) {
					response.setStatusCode(Status.OK.getStatusCode());
					response.setDescription(Status.OK.toString());
				} else {
					response.setStatusCode(Status.NO_CONTENT.getStatusCode());
					response.setDescription(Status.NO_CONTENT.toString());
				}

				connection.setAutoCommit(true);

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
				response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

				if (connection != null && !connection.isClosed()) {
					connection.rollback();
				}
			}

		} catch (Exception e) {
			LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
					+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
			response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
		}

		return response;
	}

	/**
	 * Insert record into institution table. Also, map default pwd policy,
	 * default data archival policy and default compliance policy into
	 * corresponding tables. Default pwd policy, default data archival policy
	 * and default compliance policy can be fetched from corresponding tables
	 * based on institution id 0
	 *
	 * @param institution
	 * @return
	 */
	public static CustomResponse insertRecordIntoInstitution(final Institution institution) {
		CustomResponse response = new CustomResponse();
		int numberOfRecordsInserted = 0;
		String createdBy = institution.getCreatedBy();
		String updatedBy = institution.getCreatedBy();
		String thresholdPolicy = null;

		PasswordPolicy passwordPolicy = PwdPolicyDao.fetchPwdPolicyDetailBasedOnInstitutionID(0);
		DataArchivalPolicy dataArchivalPolicy = DataArchivalPolicyDao
				.fetchDataArchivalPolicyDetailBasedOnInstitutionID(0);
		CompliancePolicy compliancePolicy = CompliancePolicyDao.fetchCompliancePolicyBasedOnInstitutionID(0);

		if (passwordPolicy != null && passwordPolicy.getPwdPolicyStatus() != null && dataArchivalPolicy != null
				&& dataArchivalPolicy.getDataArchivalPolicyStatus() != null && compliancePolicy != null
				&& compliancePolicy.getStatus() != null) {

			try (Connection connection = DBConnection.getConnection();) {
				try {
					connection.setAutoCommit(false);

					PreparedStatement pstmt = connection
							.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_INSTITUTION"));
					pstmt.setString(1, institution.getInstitutionName());
					pstmt.setString(2, institution.getAddress());
					pstmt.setString(3, institution.getContactPerson());
					pstmt.setString(4, institution.getContactNumber());
					pstmt.setString(5, institution.getCreatedBy());
					pstmt.setString(6, institution.getCreatedBy());

					numberOfRecordsInserted = pstmt.executeUpdate();

					LOGGER.info("Number of records inserted into institution table: " + numberOfRecordsInserted);

					Integer institutionId = fetchInstitutionIdOfInsertedRecord(connection, institution);

					/**
					 * Now, insert default pwd policy, thresholdPolicy, default
					 * data archival policy and default compliance policy
					 * fetched above into respective tables.
					 */
					pstmt = connection
							.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_THRESHOLD_POLICY_BY_INSTITUTION_ID"));

					pstmt.setInt(1, 0);
					ResultSet rs = pstmt.executeQuery();

					if (rs != null && rs.next()) {
						thresholdPolicy = rs.getString(TableConstants.THRESHOLD_POLICY);
						if (thresholdPolicy != null) {
							pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_THRESHOLD_POLICY"));
							pstmt.setInt(1, institutionId);
							pstmt.setString(2, thresholdPolicy);
							pstmt.setString(3, createdBy);
							pstmt.setString(4, createdBy);
							numberOfRecordsInserted = pstmt.executeUpdate();
							if (numberOfRecordsInserted > 0) {
								LOGGER.info("Number of records inserted into threshold policy table: "
										+ numberOfRecordsInserted);

							}
						}
					}
					// Now insert into pwd_policy_mgmt
					pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_PWD_POLICY_MGMT"));

					pstmt.setInt(1, institutionId);
					pstmt.setString(2, createdBy);
					pstmt.setString(3, updatedBy);
					pstmt.setInt(4, passwordPolicy.getPwdRotationInDays());
					pstmt.setInt(5, passwordPolicy.getPwdMinLength());
					pstmt.setInt(6, passwordPolicy.getPwdMaxLength());
					pstmt.setString(7, passwordPolicy.getIsCapsAllowed());
					pstmt.setString(8, passwordPolicy.getIsLowerAllowed());
					pstmt.setString(9, passwordPolicy.getIsNumericAllowed());
					pstmt.setString(10, passwordPolicy.getIsSplCharAllowed());
					pstmt.setInt(11, passwordPolicy.getRetryLoginAttemptsAllowed());
					pstmt.setInt(12, passwordPolicy.getPwdHistory());
					pstmt.setString(13, passwordPolicy.getProhibitedPasswords());
					pstmt.setString(14, passwordPolicy.getPwdPolicyStatus());
					pstmt.setString(15, passwordPolicy.getDeleteMarker());

					numberOfRecordsInserted = pstmt.executeUpdate();
					if (numberOfRecordsInserted > 0) {
						LOGGER.info(
								"Number of records inserted into pwd_policy_mgmt table: " + numberOfRecordsInserted);
					}

					// Now insert into data archival policy

					pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_DATA_ARCHIVAL_POLICY_MGMT"));

					pstmt.setInt(1, institutionId);
					pstmt.setString(2, createdBy);
					pstmt.setString(3, updatedBy);
					pstmt.setInt(4, dataArchivalPolicy.getArchivalPeriodInMonths());
					pstmt.setString(5, dataArchivalPolicy.getAutoArchivalFrequency());
					pstmt.setInt(6, dataArchivalPolicy.getAutoLogOffTimeInMinutes());
					pstmt.setInt(7, dataArchivalPolicy.getDurationToStoreAuditLogsInMonths());
					pstmt.setString(8, dataArchivalPolicy.getDataArchivalPolicyStatus());
					pstmt.setString(9, dataArchivalPolicy.getDeleteMarker());

					numberOfRecordsInserted = pstmt.executeUpdate();

					if (numberOfRecordsInserted > 0) {
						LOGGER.info("Number of records inserted into data_archival_policy_mgmt table: "
								+ numberOfRecordsInserted);
					}

					// Now insert compliance policy

					pstmt = connection.prepareStatement(SQLUtils.getSQLQuery("INSERT_INTO_COMPLIANCE_POLICY"));

					/*
					 * pstmt.setInt(1, institutionId); pstmt.setString(2,
					 * createdBy); pstmt.setString(3, updatedBy);
					 * pstmt.setInt(4,
					 * dataArchivalPolicy.getArchivalPeriodInMonths());
					 * pstmt.setString(5,
					 * dataArchivalPolicy.getAutoArchivalFrequency());
					 * pstmt.setInt(6,
					 * dataArchivalPolicy.getAutoLogOffTimeInMinutes());
					 * pstmt.setInt(7,
					 * dataArchivalPolicy.getDurationToStoreAuditLogsInMonths())
					 * ; pstmt.setString(8,
					 * dataArchivalPolicy.getDataArchivalPolicyStatus());
					 * pstmt.setString(9, dataArchivalPolicy.getDeleteMarker());
					 */

					pstmt.setInt(1, institutionId);
					pstmt.setInt(2, compliancePolicy.getCompliancePeriod());
					pstmt.setString(3, compliancePolicy.getStatus());
					pstmt.setString(4, compliancePolicy.getCreatedBy());
					pstmt.setString(5, compliancePolicy.getUpdatedBy());

					numberOfRecordsInserted = pstmt.executeUpdate();

					if (numberOfRecordsInserted > 0) {
						LOGGER.info("Number of records inserted into compliance_settings table: "
								+ numberOfRecordsInserted);
						response.setStatusCode(Status.OK.getStatusCode());
						response.setDescription(Status.OK.toString());
					}

					connection.setAutoCommit(true);

				} catch (Exception e) {
					LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
							+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**"
							+ e.getStackTrace());

					if (e.getMessage().toLowerCase().contains(DBErrorMessages.DUPLICATE_ENTRY)) {
						response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
						response.setDescription(
								Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DUPLICATE_ENTRY);
					} else {
						response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
						response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());
					}

					if (connection != null && !connection.isClosed()) {
						connection.rollback();
					}
				}

			} catch (Exception e) {
				LOGGER.error("*****Exception occurred in " + e.getStackTrace()[0].getMethodName()
						+ " method*****\n**Error Message**" + e.getMessage() + "\n**StackTrace**" + e.getStackTrace());
				response.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
				response.setDescription(Status.INTERNAL_SERVER_ERROR.toString() + ": " + e.getMessage());

			}

		} else {
			response.setStatusCode(Status.PRECONDITION_FAILED.getStatusCode());
			response.setDescription(
					Status.PRECONDITION_FAILED.toString() + ": " + DBErrorMessages.DEFAULT_POLICIES_NOT_EXIST);
		}

		return response;
	}

	/**
	 * This method will provide the institution id of inserted record
	 *
	 * @param connection
	 * @param institution
	 * @return
	 * @throws SQLException
	 */
	private static Integer fetchInstitutionIdOfInsertedRecord(final Connection connection,
			final Institution institution) throws SQLException {
		Integer institutionId = null;

		PreparedStatement pstmt = connection
				.prepareStatement(SQLUtils.getSQLQuery("SELECT_FROM_INSTITUTION_BY_INSTITUTION_NAME"));
		pstmt.setString(1, institution.getInstitutionName());

		ResultSet rs = pstmt.executeQuery();

		if (rs != null && rs.next()) {
			institutionId = rs.getInt(TableConstants.INSTITUTION_ID);
		}

		return institutionId;
	}

	/**
	 * This method will populate PasswordPolicy object from the result set and
	 * will return that object
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static PasswordPolicy extractPwdPolicyFromResultSet(final ResultSet rs) throws SQLException {
		PasswordPolicy pwdPolicy = new PasswordPolicy();

		if (rs.getInt(TableConstants.PWD_POLICY_ID) != 0) {
			pwdPolicy.setPwdPolicyId(rs.getInt(TableConstants.PWD_POLICY_ID));
			pwdPolicy.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
			pwdPolicy.setPwdRotationInDays(rs.getInt(TableConstants.PWD_POLICY_ROTATION_IN_DAYS));
			pwdPolicy.setPwdMinLength(rs.getInt(TableConstants.PWD_POLICY_MIN_LENGTH));
			pwdPolicy.setPwdMaxLength(rs.getInt(TableConstants.PWD_POLICY_MAX_LENGTH));
			pwdPolicy.setIsCapsAllowed(rs.getString(TableConstants.PWD_POLICY_IS_CAPS_ALLOWED));
			pwdPolicy.setIsLowerAllowed(rs.getString(TableConstants.PWD_POLICY_IS_LOWER_ALLOWED));
			pwdPolicy.setIsNumericAllowed(rs.getString(TableConstants.PWD_POLICY_IS_NUMERIC_ALLOWED));
			pwdPolicy.setIsSplCharAllowed(rs.getString(TableConstants.PWD_POLICY_IS_SPL_CHAR_ALLOWED));
			pwdPolicy.setRetryLoginAttemptsAllowed(rs.getInt(TableConstants.PWD_POLICY_RETRY_LOGIN_ATTEMPTS_ALLOWED));
			pwdPolicy.setPwdHistory(rs.getInt(TableConstants.PWD_POLICY_HISTORY));
			pwdPolicy.setProhibitedPasswords(rs.getString(TableConstants.PWD_POLICY_PROHIBITED_PASSWORDS));
			pwdPolicy.setPwdPolicyStatus(rs.getString(TableConstants.PWD_POLICY_STATUS));
			pwdPolicy.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_PWD_POLICY_DELETE_MARKER));
			pwdPolicy.setCreatedBy(rs.getString(TableConstants.INSTITUTION_PWD_POLICY_CREATED_BY));
			pwdPolicy.setCreatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_PWD_POLICY_CREATED_TIMESTAMP));
			pwdPolicy.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_PWD_POLICY_UPDATED_BY));
			pwdPolicy.setUpdatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_PWD_POLICY_UPDATED_TIMESTAMP));
		}

		return pwdPolicy;
	}

	/**
	 * This method will populate TermsAndConditions object from the result set
	 * and will return that object
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static TermsAndConditions extractTermsNConditionsFromResultSet(final ResultSet rs) throws SQLException {
		TermsAndConditions tnc = new TermsAndConditions();

		if (rs.getInt(TableConstants.TNC_ID) != 0) {
			tnc.setTncId(rs.getInt(TableConstants.TNC_ID));
			tnc.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
			tnc.setCreatedBy(rs.getString(TableConstants.INSTITUTION_TNC_CREATED_BY));
			tnc.setCreatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_TNC_CREATED_TIMESTAMP));
			tnc.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_TNC_UPDATED_BY));
			tnc.setUpdatedTimestamp(rs.getTimestamp(TableConstants.INSTITUTION_TNC_UPDATED_TIMESTAMP));
			tnc.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_TNC_DELETE_MARKER));
			tnc.setTncStatus(rs.getString(TableConstants.TNC_STATUS));
			tnc.setTncText(rs.getString(TableConstants.TNC_TEXT));
		}

		return tnc;
	}

	/**
	 * This method will populate DataArchivalPolicy object from the result set
	 * and will return that object
	 *
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private static DataArchivalPolicy extractDataArchivalPolicyFromResultSet(final ResultSet rs) throws SQLException {
		DataArchivalPolicy dataArchivalPolicy = new DataArchivalPolicy();

		if (rs.getInt(TableConstants.DATA_ARCHIVAL_POLICY_ID) != 0) {
			dataArchivalPolicy.setDataArchivalPolicyId(rs.getInt(TableConstants.DATA_ARCHIVAL_POLICY_ID));
			dataArchivalPolicy.setInstitutionId(rs.getInt(TableConstants.INSTITUTION_ID));
			dataArchivalPolicy.setArchivalPeriodInMonths(rs.getInt(TableConstants.DATA_ARCHIVAL_PERIOD));
			dataArchivalPolicy.setAutoArchivalFrequency(rs.getString(TableConstants.DATA_ARCHIVAL_FREQUENCY));
			dataArchivalPolicy.setAutoLogOffTimeInMinutes(rs.getInt(TableConstants.DATA_ARCHIVAL_AUTO_LOG_OFF_TIME));
			dataArchivalPolicy.setDurationToStoreAuditLogsInMonths(
					rs.getInt(TableConstants.DATA_ARCHIVAL_DURATION_TO_STORE_AUDIT_LOGS));
			dataArchivalPolicy.setDataArchivalPolicyStatus(rs.getString(TableConstants.DATA_ARCHIVAL_POLICY_STATUS));
			dataArchivalPolicy
					.setDeleteMarker(rs.getString(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_DELETE_MARKER));
			dataArchivalPolicy.setCreatedBy(rs.getString(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_CREATED_BY));
			dataArchivalPolicy.setCreatedTimestamp(
					rs.getTimestamp(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_CREATED_TIMESTAMP));
			dataArchivalPolicy.setUpdatedBy(rs.getString(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_UPDATED_BY));
			dataArchivalPolicy.setUpdatedTimestamp(
					rs.getTimestamp(TableConstants.INSTITUTION_DATA_ARCHIVAL_POLICY_UPDATED_TIMESTAMP));
		}

		return dataArchivalPolicy;
	}
}