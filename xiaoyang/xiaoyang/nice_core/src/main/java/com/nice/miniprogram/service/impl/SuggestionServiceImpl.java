package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.utils.TimeStampUtils;
import com.nice.miniprogram.dao.SuggestionMapper;
import com.nice.miniprogram.model.Suggestion;
import com.nice.miniprogram.service.SuggestionService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/17.
 */
@Service
@Transactional
public class SuggestionServiceImpl extends AbstractService<Suggestion> implements SuggestionService {
    @Resource
    private SuggestionMapper suggestionMapper;


}
