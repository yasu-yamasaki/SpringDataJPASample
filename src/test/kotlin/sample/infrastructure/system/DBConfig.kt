package sample.infrastructure.system

import org.springframework.beans.factory.FactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.TransactionManager
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
class DBConfig {
    val packages: String = this::class.java.packageName

    @Bean
    fun dataSource(): DataSource {
        return DriverManagerDataSource().apply {
            setDriverClassName("com.mysql.cj.jdbc.Driver")
            url = "jdbc:mysql://localhost:3306/sample"
            username = "sample"
            password = "sample"
        }
    }

    @Bean
    fun entityManagerFactory(dataSource: DataSource): FactoryBean<EntityManagerFactory> {
        return LocalContainerEntityManagerFactoryBean().apply {
            setDataSource(dataSource)
            setJpaPropertyMap(
                mapOf(
                    "hibernate.implicit_naming_strategy" to
                        "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy",
                    "hibernate.physical_naming_strategy" to
                        "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
                    "hibernate.hbm2ddl.auto" to "validate",
                    "hibernate.dialect" to "org.hibernate.dialect.MySQL57Dialect"
                )
            )
            setPackagesToScan(
                *listOfNotNull(
                    "com.finatext.draco37.infrastructure.common",
                    packages
                ).toTypedArray()
            )

            jpaVendorAdapter = HibernateJpaVendorAdapter()
            persistenceUnitName = "myPersistenceUnit"
        }
    }

    @Bean(name = ["transactionManager"])
    fun transactionManager(entityManagerFactory: EntityManagerFactory): TransactionManager {
        return JpaTransactionManager().apply {
            setEntityManagerFactory(entityManagerFactory)
            jpaPropertyMap
        }
    }
}
