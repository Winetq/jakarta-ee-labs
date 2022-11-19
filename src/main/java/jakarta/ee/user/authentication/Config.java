package jakarta.ee.user.authentication;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

/**
 * There are three authentication mechanisms and only one can be used at a time:
 * <ul>
 *     <li>{@link BasicAuthenticationMechanismDefinition} every secured resource is secured with basic auth mechanism,
 *     ideal for REST services (JAX-RS and Servlet). No login forms work but rest services can be used from clients.</li>
 *     <li>{@link FormAuthenticationMechanismDefinition} every secured resource is secured with form auth mechanism,
 *     ideal for HTML web pages (JSF). REST services can not be called from clients as form auth is required.</li>
 *     <li>{@link CustomFormAuthenticationMechanismDefinition} every secured resource is secured with form auth mechanism,
 *     auth form can used custom backend bean methods.</li>
 * </ul>
 */
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "Jakarta Ee Labs")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/JtaDataSource",
        callerQuery = "select password from users where login = ?",
        groupsQuery = "select role from users_roles where user = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class
)
public class Config {}
