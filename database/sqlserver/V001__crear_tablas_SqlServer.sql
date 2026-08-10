CREATE TABLE dbo.persona
(
    PersonaId BIGINT IDENTITY(1,1) NOT NULL,

    CONSTRAINT PK_Persona
        PRIMARY KEY (PersonaId)
);
GO

CREATE TABLE dbo.usuario
(
    UsuarioId BIGINT IDENTITY(1,1) NOT NULL,
    PersonaId BIGINT NULL,
    Nombre NVARCHAR(200) NOT NULL,
    Correo NVARCHAR(320) NOT NULL,
    OrigenCodigo CHAR(1) NOT NULL,
    TipoCuentaCodigo CHAR(1) NOT NULL,
    EstadoCuentaCodigo INT NOT NULL,
    FechaInicioVigencia DATE NOT NULL,
    FechaFinVigencia DATE NOT NULL,
    EstadoRegistro BIT NOT NULL,
    FechaCreacion DATETIME2(7) NOT NULL,
    ActorCreacionId BIGINT NOT NULL,
    FechaModificacion DATETIME2(7) NULL,
    ActorModificacionId BIGINT NULL,

    CONSTRAINT PK_Usuario
        PRIMARY KEY (UsuarioId),

    CONSTRAINT UQ_Usuario_Correo
        UNIQUE (Correo),

    CONSTRAINT FK_Usuario_Persona
        FOREIGN KEY (PersonaId)
            REFERENCES dbo.Persona(PersonaId),

    CONSTRAINT CK_Usuario_OrigenCodigo
        CHECK (OrigenCodigo IN ('I', 'E')),

    CONSTRAINT CK_Usuario_TipoCuentaCodigo
        CHECK (TipoCuentaCodigo IN ('P', 'A', 'S', 'O')),

    CONSTRAINT CK_Usuario_EstadoCuentaCodigo
        CHECK (EstadoCuentaCodigo IN (1, 2, 3, 4, 5, 6)),

    CONSTRAINT CK_Usuario_PeriodoVigencia
        CHECK (FechaFinVigencia >= FechaInicioVigencia),

    CONSTRAINT CK_Usuario_AuditoriaModificacion
        CHECK
            (
            (FechaModificacion IS NULL AND ActorModificacionId IS NULL)
                OR
            (FechaModificacion IS NOT NULL AND ActorModificacionId IS NOT NULL)
            )
);
GO
