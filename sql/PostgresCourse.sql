-- DROP TABLE COURSE_INPUT_KNOWLEDGE;
-- DROP TABLE COURSE;
-- DROP TABLE KNOWLEDGE;
-- DROP TABLE SKILL;
-- DROP TABLE ABILITY;
/*
    Курс: курс1, курс2 и тд.
    UUID -> UUID
    VARCHAR -> STRING
*/
CREATE TABLE COURSE (
    id UUID NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(255)
);

/*
    Знания: знание1, знание2 и тд.
    UUID -> UUID
    VARCHAR -> STRING
*/
CREATE TABLE KNOWLEDGE (
    id UUID NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(255)
);

/*
    Навыки: навык1, навык2 и тд.
    UUID -> UUID
    VARCHAR -> STRING
*/
CREATE TABLE SKILL (
    id UUID NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(255)
);

/*
    Умения: умение1, умение2 и тд.
    UUID -> UUID
    VARCHAR -> STRING
*/
CREATE TABLE ABILITY (
    id UUID NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(255)
);

/**************************************************/

/*
    Таблица связи n:n курса и входных знаний
    UUID -> UUID
*/
CREATE TABLE COURSE_INPUT_KNOWLEDGE (
    course_id UUID NOT NULL,
    knowledge_id UUID NOT NULL,

    CONSTRAINT course_input_knowledge_pk
        PRIMARY KEY (course_id, knowledge_id),

    CONSTRAINT course_input_knowledge_fk
        FOREIGN KEY (course_id)
        REFERENCES COURSE(id)
        ON DELETE CASCADE,
    CONSTRAINT input_knowledge_fk
        FOREIGN KEY (knowledge_id)
        REFERENCES KNOWLEDGE(id)
        ON DELETE CASCADE
);

/*
    Таблица связи n:n курса и входных навыков
    UUID -> UUID
*/
CREATE TABLE COURSE_INPUT_SKILL (
    course_id UUID NOT NULL,
    skill_id UUID NOT NULL,

    CONSTRAINT course_input_skill_pk
        PRIMARY KEY (course_id, skill_id),

    CONSTRAINT course_input_skill_fk
        FOREIGN KEY (course_id)
        REFERENCES COURSE(id)
        ON DELETE CASCADE,
    CONSTRAINT input_skill_fk
        FOREIGN KEY (skill_id)
        REFERENCES SKILL(id)
        ON DELETE CASCADE
);

/*
    Таблица связи n:n курса и входных умения
    UUID -> UUID
*/
CREATE TABLE COURSE_INPUT_ABILITY (
    course_id UUID NOT NULL,
    ability_id UUID NOT NULL,

    CONSTRAINT course_input_ability_pk
        PRIMARY KEY (course_id, ability_id),

    CONSTRAINT course_input_ability_fk
        FOREIGN KEY (course_id)
        REFERENCES COURSE(id)
        ON DELETE CASCADE,
    CONSTRAINT input_ability_fk
        FOREIGN KEY (ability_id)
        REFERENCES ABILITY(id)
        ON DELETE CASCADE
);

/**************************************************/

/*
    Таблица связи n:n курса и выходных знаний
    UUID -> UUID
*/
CREATE TABLE COURSE_OUTPUT_KNOWLEDGE (
    course_id UUID NOT NULL,
    knowledge_id UUID NOT NULL,

    CONSTRAINT course_output_knowledge_pk
        PRIMARY KEY (course_id, knowledge_id),

    CONSTRAINT course_output_knowledge_fk
        FOREIGN KEY (course_id)
        REFERENCES COURSE(id)
        ON DELETE CASCADE,
    CONSTRAINT output_knowledge_fk
        FOREIGN KEY (knowledge_id)
        REFERENCES KNOWLEDGE(id)
        ON DELETE CASCADE
);

/*
    Таблица связи n:n курса и входных навыков
    UUID -> UUID
*/
CREATE TABLE COURSE_OUTPUT_SKILL (
    course_id UUID NOT NULL,
    skill_id UUID NOT NULL,

    CONSTRAINT course_output_skill_pk
        PRIMARY KEY (course_id, skill_id),

    CONSTRAINT course_output_skill_fk
        FOREIGN KEY (course_id)
        REFERENCES COURSE(id)
        ON DELETE CASCADE,
    CONSTRAINT output_skill_fk
        FOREIGN KEY (skill_id)
        REFERENCES SKILL(id)
        ON DELETE CASCADE
);

/*
    Таблица связи n:n курса и входных умения
    UUID -> UUID
*/
CREATE TABLE COURSE_OUTPUT_ABILITY (
    course_id UUID NOT NULL,
    ability_id UUID NOT NULL,

    CONSTRAINT course_output_ability_pk
        PRIMARY KEY (course_id, ability_id),

    CONSTRAINT course_output_ability_fk
        FOREIGN KEY (course_id)
        REFERENCES COURSE(id)
        ON DELETE CASCADE,
    CONSTRAINT output_ability_fk
        FOREIGN KEY (ability_id)
        REFERENCES ABILITY(id)
        ON DELETE CASCADE
);

COMMIT;