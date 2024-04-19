-- done
CREATE TABLE Locations (
    LocationID INT AUTO_INCREMENT PRIMARY KEY,
    CityName VARCHAR(255) NOT NULL,
    StateName VARCHAR(255),
    CountryName VARCHAR(255) NOT NULL
);

-- done
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(255) NOT NULL UNIQUE,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    PhoneNumber VARCHAR(10) NOT NULL,
    Role ENUM('Admin', 'Client', 'Crafter') NOT NULL,
    UserImageURL VARCHAR(255),
    SignUpDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LastOnlineTime DATETIME ON UPDATE CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LocationID INT,
    Rating double DEFAULT NULL,
    NumberOfReviews INT DEFAULT 0,
    TotalSalary DECIMAL(10, 2) DEFAULT 0, 
    FOREIGN KEY (LocationID) REFERENCES Locations(LocationID) ON DELETE SET NULL ON UPDATE CASCADE
);

-- done
CREATE TABLE Categories (
    CategoryId INT AUTO_INCREMENT PRIMARY KEY,
    CategoryName VARCHAR(255) NOT NULL UNIQUE
);

-- done
CREATE TABLE Skills (
    SkillId INT AUTO_INCREMENT PRIMARY KEY,
    SkillName VARCHAR(255) NOT NULL UNIQUE,
    CategoryId INT,
    FOREIGN KEY (CategoryId) REFERENCES Categories(CategoryId) ON DELETE CASCADE ON UPDATE CASCADE
);

-- done
CREATE TABLE Crafters (
    CrafterId INT,
    Bio TEXT,
    IsAvailable BOOLEAN DEFAULT TRUE,
    Rating DOUBLE DEFAULT NULL,
    PRIMARY KEY (CrafterId),
    FOREIGN KEY (CrafterId) REFERENCES Users(UserID) ON DELETE CASCADE ON UPDATE CASCADE
);

-- done
CREATE TABLE CraftersSkills (
    CrafterId INT,
    SkillId INT,
    PRIMARY KEY (CrafterId, SkillId),
    FOREIGN KEY (CrafterId) REFERENCES Crafters(CrafterId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (SkillId) REFERENCES Skills(SkillId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Chats (
    ChatID INT AUTO_INCREMENT PRIMARY KEY,
    ChatType ENUM('private', 'proposal', 'team') NOT NULL,
    CreatedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- todo: teamChat
CREATE TABLE Projects (
    ProjectID INT AUTO_INCREMENT PRIMARY KEY,
    ClientID INT,
    CreationDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ExpectedCompletionDate DATE,
    Description TEXT,
    Status ENUM('Not Started', 'In Progress', 'Completed'),
    LocationID INT,
    TeamChatID INT,
    ImageURL VARCHAR(255),
    FOREIGN KEY (ClientID) REFERENCES Users(UserID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (LocationID) REFERENCES Locations(LocationID) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (TeamChatID) REFERENCES Chats(ChatID) ON DELETE SET NULL
);

-- done
CREATE TABLE ProjectSkills (
    ProjectID INT,
    SkillId INT,
    NumberOfCrafters INT NOT NULL,
    Complexity ENUM('easy', 'intermediate', 'hard') NOT NULL,
    Salary DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (ProjectID, SkillId),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (SkillId) REFERENCES Skills(SkillId) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ChatParticipants (
    ChatID INT,
    UserID INT,
    JoinedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ChatID, UserID),
    FOREIGN KEY (ChatID) REFERENCES Chats(ChatID) ON DELETE CASCADE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE
);

CREATE TABLE Messages (
    MessageID INT AUTO_INCREMENT PRIMARY KEY,
    ChatID INT,
    SenderID INT,
    MessageType ENUM('text', 'image', 'vedio', 'audio') NOT NULL,
    MessageText TEXT NOT NULL,
    SentAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ChatID) REFERENCES Chats(ChatID) ON DELETE CASCADE,
    FOREIGN KEY (SenderID) REFERENCES Users(UserID) ON DELETE CASCADE
);

CREATE TABLE Proposals (
    ProposalID INT AUTO_INCREMENT PRIMARY KEY,
    ProjectID INT,
    CrafterId INT,
    SkillId INT,
    Description TEXT,
    ChatID INT,
    ProposedSalary DECIMAL(10, 2),
    Status ENUM('Submitted', 'Accepted', 'Rejected') DEFAULT 'Submitted',
    SubmissionDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (CrafterId) REFERENCES Crafters(CrafterId) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ChatID) REFERENCES Chats(ChatID) ON DELETE SET NULL,
    FOREIGN KEY (SkillId) REFERENCES Skills(SkillId) ON DELETE CASCADE
);

CREATE TABLE Contracts (
    ContractID INT AUTO_INCREMENT PRIMARY KEY,
    ProposalID INT,
    StartDate DATE,
    EndDate DATE,
    AgreedSalary DECIMAL(10, 2),
    Status ENUM('Active', 'Completed', 'Terminated') DEFAULT 'Active',
    CreationDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ProposalID) REFERENCES Proposals(ProposalID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Resources (
    ResourceID INT AUTO_INCREMENT PRIMARY KEY,
    ResourceName VARCHAR(255) NOT NULL,
    Description TEXT,
    Category VARCHAR(255)
);

CREATE TABLE ClientResources (
    ClientID INT,
    ResourceID INT,
    Quantity DECIMAL(10, 2) NOT NULL,
    UnitPrice DECIMAL(10, 2) NOT NULL,
    Unit ENUM('Kilogram', 'Liter', 'Meter') NOT NULL,
    PRIMARY KEY (ClientID, ResourceID),
    FOREIGN KEY (ClientID) REFERENCES Users(UserID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ResourceID) REFERENCES Resources(ResourceID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ProjectResources (
    ProjectID INT,
    ResourceID INT,
    RequiredQuantity DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (ProjectID, ResourceID),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ResourceID) REFERENCES Resources(ResourceID) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Team (
    ProjectID INT NOT NULL,
    UserID INT NOT NULL,
    SkillId INT NULL,
    Role ENUM('Owner', 'Crafter') NOT NULL,
    JoinedAt DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ProjectID, UserID),
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE,
    FOREIGN KEY (UserID) REFERENCES Users(UserID) ON DELETE CASCADE,
    FOREIGN KEY (SkillId) REFERENCES Skills(SkillId) ON DELETE CASCADE
);

CREATE TABLE Tasks (
    TaskID INT AUTO_INCREMENT PRIMARY KEY,
    ProjectID INT NOT NULL,
    CreatedBy INT, -- UserID of the task creator, typically the project owner
    AssignedTo INT NULL, -- UserID of the crafter the task is assigned to; nullable if not yet assigned
    Title VARCHAR(255) NOT NULL,
    Description TEXT,
    Status ENUM('Not Started', 'In Progress', 'Completed') NOT NULL DEFAULT 'Not Started',
    CreationDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    DueDate DATE NULL,
    CompletionDate DATETIME NULL,
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE,
    FOREIGN KEY (CreatedBy) REFERENCES Users(UserID) ON DELETE SET NULL,
    FOREIGN KEY (AssignedTo) REFERENCES Users(UserID) ON DELETE SET NULL
);

CREATE TABLE Feedback (
    FeedbackID INT AUTO_INCREMENT PRIMARY KEY,
    ProjectID INT NOT NULL,
    FromUserID INT NOT NULL, -- UserID of the feedback giver
    ToUserID INT NOT NULL, -- UserID of the feedback receiver
    Rating INT NOT NULL CHECK (Rating >= 1 AND Rating <= 5), -- Assuming a 1-5 rating scale
    Comment TEXT,
    FeedbackDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ProjectID) REFERENCES Projects(ProjectID) ON DELETE CASCADE,
    FOREIGN KEY (FromUserID) REFERENCES Users(UserID) ON DELETE CASCADE,
    FOREIGN KEY (ToUserID) REFERENCES Users(UserID) ON DELETE CASCADE,
    UNIQUE (ProjectID, FromUserID, ToUserID) -- Ensures unique feedback entries per project and user pair
);
