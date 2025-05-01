-- 1. Create the “category” enum type
CREATE TYPE tournament_category AS ENUM (
  'amateur',
  'professional',
  'youth'
);

-- 2. Tournaments
CREATE TABLE tournament (
  id           BIGSERIAL PRIMARY KEY,
  name         VARCHAR(100)                  NOT NULL,
  sport_type   VARCHAR(50)                   NOT NULL,
  start_date   DATE                          NOT NULL,
  end_date     DATE                          NOT NULL,
  location     VARCHAR(255),
  rules        TEXT,
  created_at   TIMESTAMPTZ                   NOT NULL DEFAULT now(),
  updated_at   TIMESTAMPTZ                   NOT NULL DEFAULT now()
);

-- 3. Teams
CREATE TABLE team (
  id           BIGSERIAL PRIMARY KEY,
  name         VARCHAR(100)                  NOT NULL,
  category     tournament_category           NOT NULL DEFAULT 'amateur',
  created_at   TIMESTAMPTZ                   NOT NULL DEFAULT now(),
  updated_at   TIMESTAMPTZ                   NOT NULL DEFAULT now()
);

-- 4. Participants
CREATE TABLE participant (
  id           BIGSERIAL PRIMARY KEY,
  first_name   VARCHAR(50)                   NOT NULL,
  last_name    VARCHAR(50)                   NOT NULL,
  email        VARCHAR(100)                  NOT NULL UNIQUE,
  category     tournament_category           NOT NULL DEFAULT 'amateur',
  created_at   TIMESTAMPTZ                   NOT NULL DEFAULT now(),
  updated_at   TIMESTAMPTZ                   NOT NULL DEFAULT now()
);

-- 5. Team Membership (Roster)
CREATE TABLE team_member (
  id             BIGSERIAL PRIMARY KEY,
  team_id        BIGINT                       NOT NULL REFERENCES team(id) ON DELETE CASCADE,
  participant_id BIGINT                       NOT NULL REFERENCES participant(id) ON DELETE CASCADE,
  role           VARCHAR(50),  -- e.g. 'player', 'coach'
  jersey_number  INT,
  added_at       TIMESTAMPTZ                  NOT NULL DEFAULT now(),
  UNIQUE (team_id, participant_id)
);

-- 6. Registrations / Invitations
CREATE TABLE registration (
  id              BIGSERIAL PRIMARY KEY,
  tournament_id   BIGINT                       NOT NULL REFERENCES tournament(id) ON DELETE CASCADE,
  team_id         BIGINT                       NOT NULL REFERENCES team(id)       ON DELETE CASCADE,
  registered_at   TIMESTAMPTZ                  NOT NULL DEFAULT now(),
  status          ENUM('invited','registered','declined','cancelled') NOT NULL DEFAULT 'invited',
  invitation_sent TIMESTAMPTZ,
  UNIQUE (tournament_id, team_id)
);

-- 7. Venues
CREATE TABLE venue (
  id           BIGSERIAL PRIMARY KEY,
  name         VARCHAR(100)                  NOT NULL,
  address      VARCHAR(255),
  capacity     INT,
  created_at   TIMESTAMPTZ                   NOT NULL DEFAULT now(),
  updated_at   TIMESTAMPTZ                   NOT NULL DEFAULT now()
);

-- 8. Matches
CREATE TABLE match (
  id             BIGSERIAL PRIMARY KEY,
  tournament_id  BIGINT                       NOT NULL REFERENCES tournament(id) ON DELETE CASCADE,
  home_team_id   BIGINT                       NOT NULL REFERENCES team(id)       ON DELETE RESTRICT,
  away_team_id   BIGINT                       NOT NULL REFERENCES team(id)       ON DELETE RESTRICT,
  venue_id       BIGINT                       NOT NULL REFERENCES venue(id)      ON DELETE SET NULL,
  scheduled_at   TIMESTAMPTZ                   NOT NULL,
  status         ENUM('scheduled','live','completed','postponed','cancelled') NOT NULL DEFAULT 'scheduled',
  created_at     TIMESTAMPTZ                   NOT NULL DEFAULT now(),
  updated_at     TIMESTAMPTZ                   NOT NULL DEFAULT now(),
  CHECK (home_team_id <> away_team_id)
);

-- 9. Match Results (one-to-one with match)
CREATE TABLE match_result (
  match_id     BIGINT        PRIMARY KEY REFERENCES match(id) ON DELETE CASCADE,
  home_score   INT           NOT NULL DEFAULT 0,
  away_score   INT           NOT NULL DEFAULT 0,
  updated_at   TIMESTAMPTZ   NOT NULL DEFAULT now()
);

-- 10. Notifications
CREATE TABLE notification (
  id              BIGSERIAL PRIMARY KEY,
  tournament_id   BIGINT                       REFERENCES tournament(id) ON DELETE CASCADE,
  match_id        BIGINT                       REFERENCES match(id)      ON DELETE CASCADE,
  recipient_type  ENUM('team','participant')    NOT NULL,
  recipient_id    BIGINT                       NOT NULL,
  type            ENUM('schedule','result','update') NOT NULL,
  message         TEXT                         NOT NULL,
  sent_at         TIMESTAMPTZ                  NOT NULL DEFAULT now(),
  status          ENUM('pending','sent','failed') NOT NULL DEFAULT 'pending',
  -- recipient_id must refer to team(id) if recipient_type='team',
  -- or participant(id) if recipient_type='participant'; enforce in application logic or via
  -- a CHECK with a trigger if desired.
  CHECK (
    (recipient_type = 'team'       AND EXISTS (SELECT 1 FROM team       WHERE team.id = recipient_id))
 OR (recipient_type = 'participant' AND EXISTS (SELECT 1 FROM participant WHERE participant.id = recipient_id))
  )
);

-- 11. Optional Indexes for Performance
CREATE INDEX idx_match_scheduled_at ON match(scheduled_at);
CREATE INDEX idx_registration_status  ON registration(status);
CREATE INDEX idx_notification_status  ON notification(status);