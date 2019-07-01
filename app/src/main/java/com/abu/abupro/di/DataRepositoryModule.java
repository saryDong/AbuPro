package com.abu.abupro.di;

import com.abu.abupro.data.AnswerDataRepository;
import com.abu.abupro.data.AnswerDataSource;
import com.abu.abupro.data.ArticleDataRepository;
import com.abu.abupro.data.ArticleDataSource;
import com.abu.abupro.data.CommentDataRepository;
import com.abu.abupro.data.CommentDataSource;
import com.abu.abupro.data.MessageDataRepository;
import com.abu.abupro.data.MessageDataSource;
import com.abu.abupro.data.QuestionDataRepository;
import com.abu.abupro.data.QuestionDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

/**
 *   ArticleDataSource bindsArticleDataSource(ArticleDataRepository articleDataRepository);
 *   告诉ArticleDataSource的具体实现由ArticleDataRepository来完成
 */

@Module
public interface DataRepositoryModule {

    @Singleton
    @Binds
    ArticleDataSource bindsArticleDataSource(ArticleDataRepository articleDataRepository);

    @Singleton
    @Binds
    AnswerDataSource bindsAnswerDataSource(AnswerDataRepository answerDataRepository);

    @Singleton
    @Binds
    QuestionDataSource bindsQuestionDataSource(QuestionDataRepository questionDataRepository);

    @Singleton
    @Binds
    CommentDataSource bindsCommentDataSource(CommentDataRepository commentDataRepository);

    @Singleton
    @Binds
    MessageDataSource bindsMessageDataSource(MessageDataRepository messageDataRepository);

}
